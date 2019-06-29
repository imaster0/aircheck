# aircheck
Korzysta z https://powietrze.gios.gov.pl/pjp/content/api do sprawdzania jakości powietrza.
Umożliwia m. in. wyświetlenie:
- jakości powietrza na konkretnej stacji
- poszczególnych zanieczyszczeń powietrza
- wykresu zmian jakości w czasie (słupkowy)
- miejsc o największym / najmniejszym zanieczyszczeniu
- itd.

Przykłady użycia:
* -h  - pomoc, opis wszystkich poniższych komend
* -l
* -s "Kraków, Aleja Krasińskiego"
* -p "PM10" "Kraków, Aleja Krasińskiego" "2019-01-21" 16 00
* -c "2019-01-21" 10 00 "Kraków, Aleja Krasińskiego"
* -avg "Kraków, Aleja Krasińskiego" "PM10" "2019-01-20" 16 00 "2019-01-21" 16 00
* -low "Kraków, Aleja Krasińskiego" "2019-01-20" 16 00
* -hl "PM10"
* -ex "Kraków, Aleja Krasińskiego" "2019-01-20" 16 00
* -ho "PM10" "Kraków, Aleja Krasińskiego"
