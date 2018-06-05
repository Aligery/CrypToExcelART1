import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.ant.util.FileUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

public class Controller {
    @FXML
    private Button GetDataButton;
    @FXML
    private Button WriteOnceExcel;
    @FXML
    private TextArea TextZone;
    String url = "https://poloniex.com/public?command=returnTicker";

    public void initialize() {
        File ExcelFile = new File("C:/ResultCrypTOExcelHere/CreatedXLS.xlsx");
        ExcelFile.getParentFile().mkdirs();
        try {
            ExcelFile.createNewFile(); //Создаем файл если его нет
        } catch (IOException e) {
            System.out.println("Что-то пошло не так.");
        }
        getInfo();
    }

    public void ActionGetData(ActionEvent actionEvent) {
        setStatus();
    }

    public void WriteOnceExcelAction(ActionEvent actionEvent) {
        WriteFile();
    }

    public void getInfo() {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // Подключаемся
            con.setRequestMethod("GET");
            //Устанавливаем соединение
            con.setRequestProperty("User-Agent", "Mozilla/5.0"); // Настройки соединения
            int responseCode = con.getResponseCode(); //Получаем код
            System.out.println("\nSending 'GET' request to URL : " + url); //Логируем
            System.out.println("Response Code : " + responseCode); //Логируем
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print in String
            //System.out.println(response.toString());
            //Read JSON response and print
            //Шагаем до нужного данного, в джсон-массив нулевой элемент-джсон-значение

            JSONObject form_rates = new JSONObject(response.toString());
            //Шагаем до нужного
            System.out.println("Результат после получения данных");
            System.out.println("(c) Made by Lepa Ilya \n пара: USD_BTC \n");
            System.out.println("Последняя цена " + form_rates.getJSONObject("USDT_BTC").getDouble("last") + "\n");
            System.out.println("Лучшая цена продажи " + form_rates.getJSONObject("USDT_BTC").getDouble("lowestAsk") + "\n");
            System.out.println("Лучшая цена покупки " + form_rates.getJSONObject("USDT_BTC").getDouble("highestBid") + "\n");
            System.out.println("Рост за сутки " + form_rates.getJSONObject("USDT_BTC").getDouble("percentChange") + "\n");
            System.out.println("Объем торгов в базовой валюте " + form_rates.getJSONObject("USDT_BTC").getDouble("baseVolume") + "\n");
            System.out.println("Объем торгов в квотируемой валюте " + form_rates.getJSONObject("USDT_BTC").getDouble("quoteVolume") + "\n");
            System.out.println("Заморожена (полониксом, isFrozen, 1-да, 0 - нет) " + form_rates.getJSONObject("USDT_BTC").getDouble("isFrozen") + "\n");
            System.out.println("Высшая цена за сутки  " + form_rates.getJSONObject("USDT_BTC").getDouble("high24hr") + "\n");
            System.out.println("Низшая цена за сутки " + form_rates.getJSONObject("USDT_BTC").getDouble("low24hr") + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStatus() {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // Подключаемся
            con.setRequestMethod("GET");
            //Устанавливаем соединение
            con.setRequestProperty("User-Agent", "Mozilla/5.0"); // Настройки соединения
            int responseCode = con.getResponseCode(); //Получаем код
            System.out.println("\nSending 'GET' request to URL : " + url); //Логируем
            System.out.println("Response Code : " + responseCode); //Логируем
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print in String
            //System.out.println(response.toString());
            //Read JSON response and print
            //Шагаем до нужного данного, в джсон-массив нулевой элемент-джсон-значение

            JSONObject form_rates = new JSONObject(response.toString());
            //Шагаем до нужного
            TextZone.setText("Результат после получения данных, адрес созданного файла excel\n C:/ResultCrypTOExcelHere/CreatedXLS.xls \n"
                    + "(c) Made by Lepa Ilya \nпара: USD_BTC \n" + "Последняя цена "
                    + form_rates.getJSONObject("USDT_BTC").getDouble("last") + "\n"
                    + "Лучшая цена продажи " + form_rates.getJSONObject("USDT_BTC").getDouble("lowestAsk") + "\n"
                    + "Лучшая цена покупки " + form_rates.getJSONObject("USDT_BTC").getDouble("highestBid") + "\n"
                    + "Рост за сутки " + form_rates.getJSONObject("USDT_BTC").getDouble("percentChange") + "\n"
                    + "Объем торгов в базовой валюте " + form_rates.getJSONObject("USDT_BTC").getDouble("baseVolume") + "\n"
                    + "Объем торгов в квотируемой валюте " + form_rates.getJSONObject("USDT_BTC").getDouble("quoteVolume") + "\n"
                    + "Заморожена (полониксом, isFrozen, 1-да, 0 - нет) " + form_rates.getJSONObject("USDT_BTC").getInt("isFrozen") + "\n"
                    + "Низшая цена за сутки " + form_rates.getJSONObject("USDT_BTC").getDouble("low24hr") + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void WriteFile() {
        {
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // Подключаемся
                con.setRequestMethod("GET");
                //Устанавливаем соединение
                con.setRequestProperty("User-Agent", "Mozilla/5.0"); // Настройки соединения
                int responseCode = con.getResponseCode(); //Получаем код
                System.out.println("\nSending 'GET' request to URL : " + url); //Логируем
                System.out.println("Response Code : " + responseCode); //Логируем
                BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //print in String
                //System.out.println(response.toString());
                //Read JSON response and print
                //Шагаем до нужного данного, в джсон-массив нулевой элемент-джсон-значение
                JSONObject form_rates = new JSONObject(response.toString());
                //Шагаем до нужного
                File file = new File("C:/ResultCrypTOExcelHere/CreatedXLS.xlsx");
                FileOutputStream out = new  FileOutputStream(file);
                SXSSFWorkbook workbook = new SXSSFWorkbook(100); // Передаем ссылку куда пишем и то что это XLS
                SXSSFSheet sheet = workbook.createSheet("list1"); //Открываем 1 (нулевую) страницу

                for (int rownum = 0; rownum<100; rownum++)
                {
                    Row row = sheet.createRow(rownum);
                    for (int cellnum = 0; cellnum<100; cellnum++)
                    {
                        Cell cell = row.createCell(cellnum);
                    }
                }
                Cell cell = null;
                cell = sheet.getRow(1).getCell(1); // Говорим в какую клетку что пишем
                cell.setCellValue("Пара USD_BTC");
                cell = sheet.getRow(2).getCell(1);
                cell.setCellValue("Лучшая цена продажи ");
                cell = sheet.getRow(2).getCell(2);
                cell.setCellValue(form_rates.getJSONObject("USDT_BTC").getDouble("lowestAsk"));
                cell = sheet.getRow(3).getCell(1);
                cell.setCellValue("Лучшая цена покупки");
                cell = sheet.getRow(3).getCell(2);
                cell.setCellValue(form_rates.getJSONObject("USDT_BTC").getDouble("highestBid"));
                cell = sheet.getRow(4).getCell(1);
                cell.setCellValue("Рост за сутки");
                cell = sheet.getRow(4).getCell(2);
                cell.setCellValue(form_rates.getJSONObject("USDT_BTC").getDouble("percentChange"));
                cell = sheet.getRow(5).getCell(1);
                cell.setCellValue("Объем торгов в базовой валюте");
                cell = sheet.getRow(5).getCell(2);
                cell.setCellValue(form_rates.getJSONObject("USDT_BTC").getDouble("baseVolume"));
                cell = sheet.getRow(6).getCell(1);
                cell.setCellValue("Объем торгов в квотируемой валюте");
                cell = sheet.getRow(6).getCell(2);
                cell.setCellValue(form_rates.getJSONObject("USDT_BTC").getDouble("quoteVolume"));
                cell = sheet.getRow(7).getCell(1);
                cell.setCellValue("Заморожена");
                cell = sheet.getRow(7).getCell(2);
                cell.setCellValue(form_rates.getJSONObject("USDT_BTC").getInt("isFrozen"));
                cell = sheet.getRow(8).getCell(1);
                cell.setCellValue("Низшая цена за сутки");
                cell = sheet.getRow(8).getCell(2);
                cell.setCellValue(form_rates.getJSONObject("USDT_BTC").getDouble("low24hr"));
                cell = sheet.getRow(9).getCell(1);
                cell.setCellValue("Последняя цена");
                cell = sheet.getRow(9).getCell(2);
                cell.setCellValue(form_rates.getJSONObject("USDT_BTC").getDouble("last"));
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}