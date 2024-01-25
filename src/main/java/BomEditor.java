import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class BomEditor {
    public String dataString = "";
    private String message;
    private String operations;
    private String bomName;

    public BomEditor(String message, String operations) {
        this.message = message;
        this.operations = operations;
    }



    public BomEditor(String bomName) {
        this.bomName = bomName;
    }

    public BomEditor() {
    }

    public String getMessage() {
        return message;
    }

    public String getOperations() {
        return operations;
    }

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
}



