import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class BomEditor {
    public String dataString = "";
    private String message;
    private String operations;
    private String smdOperations;
    private String bomName;

    public BomEditor(String message, String operations, String smdOperations) {
        this.message = message;
        this.operations = operations;
        this.smdOperations = smdOperations;

    }

    public BomEditor(String dataString, String message) {
        this.dataString = dataString;
        this.message = message;
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
    public String getSmdOperations() {
        return smdOperations;
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



