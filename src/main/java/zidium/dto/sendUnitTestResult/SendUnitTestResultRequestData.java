package zidium.dto.sendUnitTestResult;

import zidium.dto.ExtentionPropertyDto;


public class SendUnitTestResultRequestData {
    
        public String UnitTestId;
        
        public Double ActualIntervalSeconds;

        public String Result;

        /// <summary>
        /// Код причины (чтобы не склеивать в одно событие разные проблемы)
        /// </summary>
        public Integer ReasonCode;

        /// <summary>
        /// Комментарий к результату юнит-теста (необязательно)
        /// </summary>
        public String Message;

        public ExtentionPropertyDto[] Properties;
    }
