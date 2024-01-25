import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class BomResult {


    public BomEditor bomEditor(String bomName) {
        BomEditor bomEditor = new BomEditor(bomName);
        //Scanner scanner = new Scanner(System.in);

        String data = "";
        String operation = "";
        String operationList = " ";
        String smdOperations = " ";

        try {

            String currentWorkingDir = System.getProperty("user.dir");

            Workbook workbookOut = new XSSFWorkbook(); // Create a new Workbook (XSSFWorkbook for .xlsx format)
            Sheet sheetOut = workbookOut.createSheet("Sheet1");

            String bomForEdit = currentWorkingDir + File.separator + bomName + "-BOM.xlsx";
            String editedBom = currentWorkingDir + File.separator + bomName + ".xlsx";


            FileInputStream fis = new FileInputStream(bomForEdit);

            Workbook workbookIn = new XSSFWorkbook(fis);
            Sheet sheetIn = workbookIn.getSheet("Sheet1");
            int rowCount = 0;
            FileOutputStream fileOutputStream = new FileOutputStream(editedBom);
            Row rowOut;
            Cell cellOut;
            int j = 0;
            boolean skipFirst = true;
            boolean good = true;  //
            boolean validOperations = false;
           // boolean containOperation = false;

            Iterator<Row> rowIterator = sheetIn.iterator();
            while (rowIterator.hasNext()) {


                Row rowIn = rowIterator.next();
                if (skipFirst) {
                    skipFirst = false;
                    continue;
                }

                Row firstRow = sheetIn.getRow(0);
                rowOut = sheetOut.createRow(rowCount);
                rowCount++;
                j = 0;
                for (int i = 0; i < 8; i++) {
                    Cell cellOperation = rowIn.getCell(2);
                    operation = bomEditor.getType(cellOperation);

                    if(!operationList.contains(operation)){
                        operationList = operationList + " [" +  operation + "]  ";
                    }
                    if (operation.equalsIgnoreCase("10.0")
                            || operation.equalsIgnoreCase("20.0")
                            || operation.equalsIgnoreCase("11.0")
                            || operation.equalsIgnoreCase("21.0")
                    ) { //||operation.equalsIgnoreCase("ToOperation")
                        validOperations = true;
                        if(!smdOperations.contains(operation)){
                            smdOperations = smdOperations + " [" +  operation + "]  ";
                        }



                        Cell cellCheck = firstRow.getCell(i); //row.getCell(collumnNumber);
                        data = bomEditor.getType(cellCheck);

                        if (data.equalsIgnoreCase("PartNumber")
                                || data.equalsIgnoreCase("PartDescription")
                                || data.equalsIgnoreCase("Designator")
                                || data.equalsIgnoreCase("Replacement")) {

                            Cell cellIn = rowIn.getCell(i); //row.getCell(collumnNumber);

                            if (data.equalsIgnoreCase("Designator")) {
                                data = bomEditor.getType(cellIn).replace(" ", "").replace("..", "-");
                                if (!data.matches("[\\x22-\\x2C\\x2E\\x2D\\x30-\\x39\\x41-\\x5A\\x61-\\x7A]+")) {
                                    good = false;
                                }
                            } else {
                                data = bomEditor.getType(cellIn);
                            }

//                            Cell cellOperation = rowIn.getCell(2);
//                            operation = getType(cellOperation);
//                            if(operation.equalsIgnoreCase("10.0")
//                                    || operation.equalsIgnoreCase("20.0")
//                                    ){ //||operation.equalsIgnoreCase("ToOperation")
//                                validOperations = true;
                            cellOut = rowOut.createCell(j);
                            j++;
                            cellOut.setCellValue(data);
                        } // last if

                    }
                }
            }

            if (good && validOperations) {
                workbookOut.write(fileOutputStream);

                //workbookOut.write(fileOutputStream);


                String notepadOutputFile = currentWorkingDir + "\\notepad" + File.separator + bomName + ".txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(notepadOutputFile));

                for (Row row : sheetOut) {
                    for (Cell cell : row) {
                        writer.write(bomEditor.getType(cell) + "\t"); // Separate values with tabs
                    }
                    writer.newLine(); // Move to the next line for each row
                }

                writer.close();
                data = bomName + " izveide izdevusies";
            } else {
                if (!good) {
                    data = "nezināmi simboli, izveide jāveic manuāli";
                } else {
                    data = "nav ne 10, ne 20 operācijas, izveide jāveic manuāli";
                }

            }
            workbookOut.close();
            workbookIn.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            data = bomName + " fails nav atrasts";


        } catch (Exception e) {
            data = bomName + " izveide neizdevas!!!";
        }


        int endIndex = operationList.length() - 2;
        String operationListTrimmed  = operationList.substring(0, endIndex);
        operationList = operationListTrimmed;
//        endIndex = smdOperations.length() - 2;
//        operationListTrimmed  = smdOperations.substring(0, endIndex);
//        smdOperations = operationListTrimmed;
        System.out.println(smdOperations);


        return new BomEditor(data, operationList, smdOperations);

    }


    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }


}
