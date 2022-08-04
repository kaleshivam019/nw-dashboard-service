package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.constants.DashboardConstants;
import com.ey.nwdashboard.entity.ProjectEntity;
import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.entity.VacationEntity;
import com.ey.nwdashboard.model.*;
import com.ey.nwdashboard.service.FetchReportService;
import com.ey.nwdashboard.service.ProjectDBService;
import com.ey.nwdashboard.service.UserDBService;
import com.ey.nwdashboard.service.VacationDBService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FetchReportServiceImpl implements FetchReportService {

    @Autowired
    ProjectDBService projectDBService;

    @Autowired
    UserDBService userDBService;

    @Autowired
    VacationDBService vacationDBService;

    @Value("${report.dir.path}")
    private String DIR_PATH;

    @Value("${report.filename}")
    private String FILE_NAME;

    @Override
    public ResponseEntity fetchReport() {
        try{
            //Setting start date & end date of current month & next month
            LocalDate currentMonthStartDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LocalDate currentMonthEndDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
            LocalDate nextMonthStartDate = LocalDate.now().plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
            LocalDate nextMonthEndDate = LocalDate.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

            //Prepare Response
            FetchReportResponse fetchReportResponse = new FetchReportResponse();

            //Get all the projects
            List<ProjectEntity> projects = projectDBService.getAllProjects();

            //Get all the users
            List<UserEntity> users = userDBService.getAllUsers();

            //For each project get all the users & their leaves
            if(null != projects){

                List<FetchReportProjectModel> projectModels = new ArrayList<>();

                projects.forEach(projectEntity -> {
                    String projectName = projectEntity.getProjectName();

                    FetchReportProjectModel fetchReportProjectModel = new FetchReportProjectModel();
                    fetchReportProjectModel.setProjectName(projectName);

                    //Get project specific users
                    if(null != users){

                        List<FetchReportUserModel> userModels = new ArrayList<>();

                        users.forEach(user -> {
                            if(user.getUserProjectName().equalsIgnoreCase(projectName)){
                                String userGPN = user.getUserGPN();
                                String userName = user.getUserName();

                                FetchReportUserModel fetchReportUserModel = new FetchReportUserModel();
                                fetchReportUserModel.setUserName(userName);

                                FetchReportLeaveModel fetchReportLeaveModel = new FetchReportLeaveModel();
                                List<String> currentMonthLeaves = new ArrayList<>();
                                List<String> nextMonthLeaves = new ArrayList<>();

                                //Get vacations for specific user
                                List<VacationEntity> userVacations = vacationDBService.getVacations(userGPN);

                                //check if vacations is in between startDate & endDate
                                if (null != userVacations){
                                    userVacations.forEach(userVacation -> {
                                        //Get the vacation date & parse it LocalDate for comparison
                                        LocalDate vacationDate = LocalDate.parse(userVacation.getVacationDate().toString());

                                        //Check for current month vacations & next month vacation
                                        if((vacationDate.isEqual(currentMonthStartDate)) ||
                                                (vacationDate.isEqual(currentMonthEndDate)) ||
                                                (vacationDate.isAfter(currentMonthStartDate) && vacationDate.isBefore(currentMonthEndDate))){
                                            currentMonthLeaves.add(vacationDate.toString());
                                        }else if((vacationDate.isEqual(nextMonthStartDate)) ||
                                                (vacationDate.isEqual(nextMonthEndDate)) ||
                                                (vacationDate.isAfter(nextMonthStartDate) && vacationDate.isBefore(nextMonthEndDate))){
                                            nextMonthLeaves.add(vacationDate.toString());
                                        }
                                    });

                                    //Sort leaves with natural sorting order
                                    currentMonthLeaves.sort(Comparator.naturalOrder());
                                    nextMonthLeaves.sort(Comparator.naturalOrder());

                                    fetchReportLeaveModel.setCurrentMonth(currentMonthLeaves);
                                    fetchReportLeaveModel.setNextMonth(nextMonthLeaves);

                                    fetchReportUserModel.setLeaves(fetchReportLeaveModel);
                                }
                                userModels.add(fetchReportUserModel);
                            }
                        });
                        fetchReportProjectModel.setUsers(userModels);
                    }
                    projectModels.add(fetchReportProjectModel);
                });
                fetchReportResponse.setProjects(projectModels);
            }
            if(null != fetchReportResponse){
                return new ResponseEntity(fetchReportResponse, HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    /**
     * This method is responsible for creating and writing data in Excel sheet
     * @param startDate
     * @param endDate
     * @param userGPN
     * @return
     */
    @Override
    public ResponseEntity generateReport(String startDate, String endDate, String userGPN) {
        try{
            //Create a workbook
            Workbook workbook = new XSSFWorkbook();

            //Write date to excel file
            writeFinalData(workbook, startDate, endDate, userGPN);

            //Writing output stream
            OutputStream fileOut = new FileOutputStream(DIR_PATH+FILE_NAME);
            workbook.write(fileOut);
        }catch (InvalidFormatException e){
            e.printStackTrace();
            return new ResponseEntity(new ReportResponse("Failed",e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (IOException c){
            c.printStackTrace();
            return new ResponseEntity(new ReportResponse("Failed",c.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(new ReportResponse("Success", "File created!"), HttpStatus.OK);
    }

    /**
     * This method is responsible to download excel file and send it as response in get rest api
     * @return
     */
    @Override
    public ResponseEntity downloadFile() {
        byte[] byteArray;  // data comes from external service call in byte[]
        try {
            byteArray = Files.readAllBytes(Paths.get(DIR_PATH + FILE_NAME));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    /*.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + FILE_NAME + "\"")*/
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FILE_NAME)
                    .body(byteArray);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void writeFinalData(Workbook workbook, String startDate, String endDate, String userGPN){
        //Parse string dates to LocalDate object
        var localDateRef = new Object() {
            LocalDate localStartDate = null;
            LocalDate localEndDate = null;
        };
        if((null != startDate && !startDate.isBlank()) && (null != endDate && !endDate.isBlank())){
            localDateRef.localStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            localDateRef.localEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }else{
            //Assign start date & end date of the year if date coming as null or empty
            localDateRef.localStartDate = LocalDate.of(LocalDate.now().getYear(), 01, 01);
            localDateRef.localEndDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);
        }

        //Create sheet
        workbook.createSheet("NW PLT Leave Tracker");

        //Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        //Create Headers for the report
        workbook = createHeaders(workbook, sheet, localDateRef.localStartDate, localDateRef.localEndDate);

        //Save vacations for users based on userGPN, startDate & endDate
        List<UserEntity> users = userDBService.getAllUsers();
        if(null != userGPN && !userGPN.isBlank() &&
                null != localDateRef.localStartDate &&
                null != localDateRef.localEndDate){ //For single requested user
            Optional<UserEntity> userEntityOptional = Optional.ofNullable(users.stream().filter(userEntity -> userEntity.getUserGPN().equals(userGPN)).findFirst().orElse(null));
            if(null != userEntityOptional && !userEntityOptional.isEmpty()){
                createAndInsertRow(2, workbook, sheet, userEntityOptional.get().getUserName(), userGPN, localDateRef.localStartDate, localDateRef.localEndDate);
            }
        }else if((null == userGPN || userGPN.isBlank()) &&
                null != localDateRef.localStartDate &&
                null != localDateRef.localEndDate){ //For multiple users
            int rowNumber = 2;
            for (UserEntity userEntity : users){
                createAndInsertRow(rowNumber, workbook, sheet, userEntity.getUserName(), userEntity.getUserGPN(), localDateRef.localStartDate, localDateRef.localEndDate);
                rowNumber++;
            }
        }

    }

    /**
     * This method is responsible to create & insert row in the excel sheet
     * @param rowNumber
     * @param workbook
     * @param sheet
     * @param userGPN
     * @param userName
     * @param localStartDate
     * @param localEndDate
     */
    private void createAndInsertRow(int rowNumber, Workbook workbook, Sheet sheet, String userName, String userGPN, LocalDate localStartDate, LocalDate localEndDate) {
        List<VacationEntity> userVacations = vacationDBService.getVacations(userGPN);
        LocalDate finalLocalStartDate = localStartDate;
        userVacations = userVacations.stream().filter(vacationEntity ->
                (vacationEntity.getVacationDate().isEqual(finalLocalStartDate)) ||
                        (vacationEntity.getVacationDate().isEqual(localEndDate)) ||
                        (vacationEntity.getVacationDate().isAfter(finalLocalStartDate) && vacationEntity.getVacationDate().isBefore(localEndDate))).collect(Collectors.toList());

        //Fill map with default values
        LinkedHashMap<String, String> userVacationMap = new LinkedHashMap<>();
        while(!localStartDate.isAfter(localEndDate)){{
            if(localStartDate.getDayOfWeek().name().equals(DashboardConstants.DAY_SUNDAY) ||
                    localStartDate.getDayOfWeek().name().equals(DashboardConstants.DAY_SATURDAY)){
                userVacationMap.put(localStartDate.toString(), DashboardConstants.FLAG_W);
            }else {
                userVacationMap.put(localStartDate.toString(), DashboardConstants.FLAG_N);
            }
            localStartDate = localStartDate.plusDays(1);
        }}
        //Map user vacations in user vacation map
        if(null != userVacations && !userVacations.isEmpty()){
            userVacations.forEach(vacationEntity -> {
                userVacationMap.put(vacationEntity.getVacationDate().toString(), DashboardConstants.FLAG_Y);
            });
        }
        //Write vacations in sheet - START
        CellStyle vacationCellStyle = getCellStyle(sheet, IndexedColors.LIGHT_GREEN.getIndex());
        CellStyle weekEndCellStyle = getCellStyle(sheet, IndexedColors.GREY_25_PERCENT.getIndex());
        CellStyle normalCellStyle = getCellStyle(sheet, IndexedColors.WHITE.getIndex());

        //Create row
        sheet.createRow(rowNumber);
        Row row = sheet.getRow(rowNumber);

        //First cell
        Cell firstCell = row.createCell(0);
        firstCell.setCellStyle(normalCellStyle);
        firstCell.setCellValue(userName);
        sheet.autoSizeColumn(0);


        //Second cell
        Cell secondCell = row.createCell(1);
        secondCell.setCellStyle(normalCellStyle);
        secondCell.setCellValue(userGPN);
        sheet.autoSizeColumn(1);

        int vacationCellNumber = 2;
        for (Map.Entry<String, String> vacation : userVacationMap.entrySet()){
            String type = vacation.getValue();
            if(type.equals(DashboardConstants.FLAG_Y)){
                Cell vacationCell = row.createCell(vacationCellNumber);
                vacationCellStyle.setAlignment(HorizontalAlignment.CENTER);
                vacationCell.setCellStyle(vacationCellStyle);
                vacationCell.setCellValue(DashboardConstants.FLAG_V);
            }else if(type.equals(DashboardConstants.FLAG_W)){
                Cell weekendCell = row.createCell(vacationCellNumber);
                weekendCell.setCellStyle(weekEndCellStyle);
                weekendCell.setCellValue("");
            }else if(type.equals(DashboardConstants.FLAG_N)){
                Cell normalCell = row.createCell(vacationCellNumber);
                normalCell.setCellStyle(normalCellStyle);
                normalCell.setCellValue("");
            }
            vacationCellNumber++;
        }
        //Write vacations in sheet - END
    }

    /**
     * This method is responsible to create headers for the report
     * @param workbook
     * @param sheet
     * @param localStartDate
     * @param localEndDate
     * @return
     */
    private Workbook createHeaders(Workbook workbook, Sheet sheet, LocalDate localStartDate, LocalDate localEndDate) {
        //Create first & second row in the sheet
        sheet.createRow(0);
        sheet.createRow(1);

        //Get the first & second row
        Row firstRow = sheet.getRow(0);
        Row secondRow = sheet.getRow(1);

        //Create UserName & UserGPN header
        secondRow.createCell(0);
        secondRow.createCell(1);
        Cell nameCell = secondRow.getCell(0);
        Cell gpnCell = secondRow.getCell(1);
        CellStyle nameAndGPNCellStyle = getCellStyle(sheet, IndexedColors.BLACK.getIndex());

        Font nameAndGPNFont = workbook.createFont();
        nameAndGPNFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        nameAndGPNCellStyle.setFont(nameAndGPNFont);

        nameCell.setCellValue(DashboardConstants.HEADER_NAME);
        nameCell.setCellStyle(nameAndGPNCellStyle);
        gpnCell.setCellValue(DashboardConstants.HEADER_GPN);
        gpnCell.setCellStyle(nameAndGPNCellStyle);


        //Set styles for dates & day header
        CellStyle dayDateHeaderStyle = getCellStyle(sheet, IndexedColors.YELLOW.getIndex());

        //create dates & day header
        int firstSecondRowCellNumber = 2;
        while(!localStartDate.isAfter(localEndDate)){
            //convert it into string to save in excel sheet
            String date = localStartDate.toString();
            String day = localStartDate.getDayOfWeek().name();

            //Create cell for first & second row
            firstRow.createCell(firstSecondRowCellNumber);
            Cell firstRowCell = firstRow.getCell(firstSecondRowCellNumber);
            firstRowCell.setCellStyle(dayDateHeaderStyle);
            firstRowCell.setCellValue(day);

            secondRow.createCell(firstSecondRowCellNumber);
            Cell secondRowCell = secondRow.getCell(firstSecondRowCellNumber);
            secondRowCell.setCellStyle(dayDateHeaderStyle);
            secondRowCell.setCellValue(date);

            //Auto increment the column size
            sheet.autoSizeColumn(firstSecondRowCellNumber);

            //Increment the date by +1
            localStartDate = localStartDate.plusDays(1);

            //Increment second row cell number
            firstSecondRowCellNumber++;
        }
        return workbook;
    }

    /**
     * Returns the cell style
     * @param sheet
     * @param colorIndex
     * @return
     */
    private CellStyle getCellStyle(Sheet sheet, short colorIndex) {
        CellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFillForegroundColor(colorIndex);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        return style;
    }

}
