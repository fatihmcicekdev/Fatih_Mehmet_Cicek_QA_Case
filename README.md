# Fatih Mehmet Cicek QA Case

Insider QA mühendis pozisyonu için hazırlanmış test otomasyon projesi.

## Projeler

### 1. Insider-Test-Automation

Selenium WebDriver ile UI test otomasyonu.

**Gereksinimler:**

- Java 11+
- Maven
- Chrome/Firefox browser

**Çalıştırma:**

```bash
cd Insider-Test-Automation
mvn clean test
```

**Raporlar:** `target/surefire-reports/` ve `screenshots/`

---

### 2. Insider-API-Test

RestAssured ile API test otomasyonu.

**Gereksinimler:**

- Java 11+
- Maven

**Çalıştırma:**

```bash
cd Insider-API-Test
mvn clean test
```

**Raporlar:** `target/extent-reports/` ve `target/surefire-reports/`

---

### 3. Insider-Load-Test

JMeter ile performans testi.

**Gereksinimler:**

- Apache JMeter 5.6+

**Çalıştırma:**

```bash
cd Insider-Load-Test
jmeter -n -t amazon-search.jmx -l results.jtl
```

## Kurulum

```bash
git clone <repository-url>
cd Fatih_Mehmet_Cicek_QA_Case
```

Her proje için ilgili dizine gidip yukarıdaki komutları çalıştırın.
