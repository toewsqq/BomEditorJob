import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Scanner;

public class BomEditor {
    public String dataString = "";


//    public static void main(String[] args) {
//
//        BomEditor bomEditor = new BomEditor();
//        bomEditor.ReadAllExcel();
//
//    }

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

        //System.out.print("Enter BOM name: ");

        //bomName = scanner.nextLine();

        //scanner.close();


        String data = "";
        String operation = "";
        try {
            Workbook workbookOut = new XSSFWorkbook(); // Create a new Workbook (XSSFWorkbook for .xlsx format)
            Sheet sheetOut = workbookOut.createSheet("Sheet1");
            String bomForEdit = "C:\\Users\\Janis\\Desktop\\BomEditor\\New folder\\" + bomName + "-BOM.xlsx";
            String editedBom = "C:\\Users\\Janis\\Desktop\\" + bomName + ".xlsx";

            FileInputStream fis = new FileInputStream(bomForEdit);
            Workbook workbookIn = WorkbookFactory.create(fis);
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
                    //System.out.println(rowIn);
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
            System.out.println("file saved");
            workbookOut.close();
            workbookIn.close();

            String notepadOutputFile = "C:\\Users\\Janis\\Desktop\\" + bomName + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(notepadOutputFile));

            for (Row row : sheetOut) {
                for (Cell cell : row) {
                    writer.write(getType(cell) + "\t"); // Separate values with tabs
                }
                writer.newLine(); // Move to the next line for each row
            }

            writer.close();
            //System.out.println("Text file saved for Notepad: " + notepadOutputFile);
            data = bomName+ " izveide izdevusies";

        }catch(Exception e){
            data = bomName + " izveide neizdevas!!!";
            //System.out.println("Read failed");
            e.printStackTrace();
        }
        return data;

    }


}
