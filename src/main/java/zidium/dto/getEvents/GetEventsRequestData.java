package zidium.dto.getEvents;

import java.util.Date;


public class GetEventsRequestData {

        // Id владельца
        public String OwnerId;
       
        // Начало диапазона поиска
        public Date From;

        // Конец диапазона поиска
        public Date To;

        // Уровни важности. Если не заполнено, то не учитывается в поиске.
        public String[] Importance;

        // Системное имя типа
        public String TypeSystemName;

        // Строка для поиска. Выполняется поиск событий, которые содержат указнную подстроку.
        public String SearchText;

        // Категория для поиска
        public String Category;

        // Максимальное количество записей в ответе
        // Не может быть больше 1000
        public Integer MaxCount;
}
