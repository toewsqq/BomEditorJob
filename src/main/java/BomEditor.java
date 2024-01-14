import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class BomEditor {
    public String dataString = "";


    public String getType(Cell cellCheck){
        if (cellCheck != null) {
            CellType cellCheckCellType = cellCheck.getCellType();
            if (cellCheckCellType == CellType.STRING) {
                dataString = cellCheck.getStringCellValue();
            } else if (cellCheckCellType == CellType.NUMERIC) {
                dataString = String.valueOf(cellCheck.getNumericCellValue());
            }
        }
        return dataString;
    }

    public String ReadAllExcel(String bomName){
        Scanner scanner = new Scanner(System.in);

        String data = "";
        String operation = "";
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

                Iterator<Row> rowIterator = sheetIn.iterator();
                while(rowIterator.hasNext()) {


                    Row rowIn = rowIterator.next();
                    if(skipFirst){
                        skipFirst = false;
                        continue;
                    }

                    Row firstRow = sheetIn.getRow(0);
                    rowOut = sheetOut.createRow(rowCount);
                    rowCount++;
                    j=0;
                    for(int i = 0;i < 8;i++) {

                        Cell cellCheck = firstRow.getCell(i); //row.getCell(collumnNumber);
                        data = getType(cellCheck);

                        if(data.equalsIgnoreCase("PartNumber")
                        || data.equalsIgnoreCase("PartDescription")
                        || data.equalsIgnoreCase("Designator")
                        || data.equalsIgnoreCase("Replacement")){

                            Cell cellIn = rowIn.getCell(i); //row.getCell(collumnNumber);

                            if(data.equalsIgnoreCase("Designator")){
                                data = getType(cellIn).replace(" ", "").replace("..", "-");
                            }
                            else{
                                data = getType(cellIn);
                            }

                            Cell cellOperation = rowIn.getCell(2);
                            operation = getType(cellOperation);
                            if(operation.equalsIgnoreCase("10.0")
                                    || operation.equalsIgnoreCase("20.0")
                                    ||operation.equalsIgnoreCase("ToOperation")){

                                cellOut = rowOut.createCell(j);
                                j++;
                                cellOut.setCellValue(data);
                            }
                        }
                    }
                }
            workbookOut.write(fileOutputStream);
            workbookOut.close();
            workbookIn.close();

            String notepadOutputFile = currentWorkingDir + "\\notepad" + File.separator + bomName + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(notepadOutputFile));

            for (Row row : sheetOut) {
                for (Cell cell : row) {
                    writer.write(getType(cell) + "\t"); // Separate values with tabs
                }
                writer.newLine(); // Move to the next line for each row
            }

            writer.close();
            data = bomName+ " izveide izdevusies";

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            data = bomName+ " fails nav atrasts";


        }catch(Exception e){
            data = bomName + " izveide neizdevas!!!";
        }
        return data;

    }

    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }


}
