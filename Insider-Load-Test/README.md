# Insider Load Test

JMeter ile Amazon arama performans testi.

## Çalıştırma

```bash
jmeter -n -t amazon-search.jmx -l results.jtl
```

## İçerik

- `amazon-search.jmx` - JMeter test planı
- `keywords.csv` - Arama kelimeleri
