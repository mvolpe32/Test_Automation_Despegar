package com.mvolpe.test.selenium.newtours;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.YearMonth;

public class Tests {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://www.despegar.com.ar/");
        driver.manage().window().maximize();

    }

    @Test
    public void test1() throws InterruptedException {
        String[][] tabSelection2 = {{"Alojamiento",".HOTELS","Alojamiento"},{"Vuelos",".FLIGHTS","Vuelos"},{"Paquetes",".PACKAGES","Paquetes turísticos"}};


//        Map<String, String> tabSelection = new HashMap<String, String>();
//        tabSelection.put("Alojamiento",".HOTELS");
//        tabSelection.put("Vuelos",".FLIGHTS");
//        tabSelection.put("Paquetes",".PACKAGES");


        String[] listaCiudades = {"Brasilia, Distrito Federal, Brasil","Rio de Janeiro, Rio de Janeiro, Brasil","Londres, " +
                "Inglaterra, Reino Unido","Mánchester, Inglaterra, Reino Unido"};
        String[] listaAeropuertos = {"Aeropuerto Santos Dumont, Rio de Janeiro, Brasil", "Aeropuerto Internacional Galeão Antonio Carlos Jobim" +
                ", Rio de Janeiro, Brasil", "Aeropuerto London Heathrow, Londres, Reino Unido", "Aeropuerto London Gatwick, Londres, Reino Unido"};
        String[] sugerencia = {"Brasil","Inglaterra"};
        String ciudadAeropuerto;

        int dayNumber = (int)Math.floor(Math.random()*28)+1;


        selectionTab(tabSelection2[1][1],tabSelection2[1][2]);
        //Limpio el valor por defecto del origen
        driver.findElement(By.cssSelector("input[class='input-tag sbox-main-focus sbox-bind-reference-flight-roundtrip-" +
                "origin-input sbox-primary sbox-places-first places-inline'][placeholder='Ingresá desde dónde viajas']")).clear();
        cargaCiudad(sugerencia[0],listaCiudades[0]); //Origen


//        //Ingreso Roma en el destino y cargo las sugerencias
        driver.findElement(By.cssSelector("input[class='input-tag sbox-main-focus sbox-bind-reference-flight-roundtrip-" + "destination-input " +
                "sbox-secondary sbox-places-second places-inline'][placeholder='Ingresá hacia dónde viajas']")).sendKeys("Buenos Aires");
        //Espero que se desplieguen las sugerencias
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(3000L));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Ciudades')]")));
        //Clickeo en Roma como destino
        driver.findElement(By.xpath("//span[contains(.,'Buenos Aires, Ciudad de Buenos Aires, Argentina')]")).click();

        //Selecciono fecha de ida

        //Abro panel
        driver.findElement(By.cssSelector(".sbox-bind-event-click-start-date")).click();

        //Espero que se despliegue el calendario
        wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("._dpmg2--show")));

        //Elijo el dia de hoy
        driver.findElement(By.cssSelector("._dpmg2--show ._dpmg2--months ._dpmg2--month-active ._dpmg2--dates ._dpmg2--today")).click();

        //Selecciono fecha de vuelta

        //Tomo el atributo del mes corriente data-month que contiene formato YYYY-MM y calculo el mes siguiente
        WebElement currentMonth = driver.findElement(By.cssSelector("._dpmg2--show ._dpmg2--month-active"));
        String currentDataMonth = currentMonth.getAttribute("data-month");
        YearMonth currentYearMonth = YearMonth.parse(currentDataMonth);
        YearMonth nextYearMonth = currentYearMonth.plusMonths(1);
        Thread.sleep(500);
        //Busco el WebElement del mes siguiente y clickeo un dia random de ese mes
        driver.findElement(By.cssSelector("._dpmg2--show ._dpmg2--month[data-month='"+nextYearMonth+"'] ._dpmg2--available:nth-of-type("+dayNumber+")")).click();

        Thread.sleep(1000);

        //Despliego panel para agregar personas
        driver.findElement(By.cssSelector(".sbox-bind-event-click-hotel-passengers-input .sbox-passengers-container")).click();
        wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='_pnlpk-main _pnlpk-panel _pnlpk" +
                "-panel--popup _pnlpk-panel--mobile _pnlpk-panel--show']")));
        //Agrego 2 adultos
        driver.findElement(By.cssSelector("._pnlpk-panel--show ._pnlpk-itemRow:first-of-type .sbox-3-icon-plus")).click();
        driver.findElement(By.cssSelector("._pnlpk-panel--show ._pnlpk-itemRow:first-of-type .sbox-3-icon-plus")).click();
        //Agrego 2 menores
        driver.findElement(By.cssSelector("._pnlpk-panel--show ._pnlpk-itemRow:nth-of-type(2) .sbox-3-icon-plus")).click();
        driver.findElement(By.cssSelector("._pnlpk-panel--show ._pnlpk-itemRow:nth-of-type(2) .sbox-3-icon-plus")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[2]/div/div[1]/div/div[3]/div[1]/div[2]/div/div/select")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[2]/div/div[1]/div/div[3]/div[1]/div[2]/div/div/select/option[5]")).click();
        Thread.sleep(500);
        //Selecciono edad del menor
        driver.findElement(By.cssSelector("._pnlpk-panel--show ._pnlpk-minors-age-select-wrapper ._pnlpk-minor-age-select" +
                "-last-item ._pnlpk-select-minor-age .sbox-3-select")).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("._pnlpk-panel--show ._pnlpk-minors-age-select-wrapper ._pnlpk-minor-age-select-last" +
                "-item ._pnlpk-select-minor-age .sbox-3-select .select-tag option[value='4']")).click();

        //Clickeo Buscar vuelos
        driver.findElement(By.cssSelector(".-mt5-l .sbox-3-btn")).click();

        //Espero que termine de cargar vuelos disponibles
        wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".fast-filters")));

    }

    public void selectionTab(String tabClick, String tabWait) {
        driver.findElement(By.cssSelector(tabClick)).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000L));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'" + tabWait + "')]")));
    }


    public void cargaCiudad(String sugerencia, String ciudadAeropuerto) {

        //Ingreso sugerencia
        driver.findElement(By.cssSelector(".sbox-mobile-body .sbox-row .-mb4-s .sbox-input-container .sbox-3-validation " +
                ".sbox-bind-reference-flight-roundtrip-origin-input")).sendKeys(sugerencia);

        //        Espero a que el menu de sugerencias se termine de cargar
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(3000L));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Ciudades')]")));

        //Clickeo en la opcion del origen
        driver.findElement(By.xpath("//span[contains(.,'" + ciudadAeropuerto + "')]")).click();

    }


    @After
    public void tearDown() throws InterruptedException {

        driver.close();

    }
}