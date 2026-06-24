// Дефинираме пина, на който преместихме оранжевия кабел (GPIO 25)
const int doorSensorPin = 25;

// Дефинираме пина за вградения светодиод на платката LilyGO T-A7670
const int boardLED = 12;

void setup() {
 // Настройваме пина на магнитите като вход с вътрешна защита
  pinMode(doorSensorPin, INPUT_PULLUP);
  
  // Настройваме пина на лампичката като ИЗХОД, за да я управляваме
  pinMode(boardLED, OUTPUT);

  // Проверка веднага при пускане (Start up)
  if (digitalRead(doorSensorPin) == LOW) {
    digitalWrite(boardLED, HIGH); // Допрени са -> свети
  } else {
    digitalWrite(boardLED, LOW);  // Разделени са -> не свети
  }
}


void loop() {
  // Проверяваме състоянието в реално време
  int currentStatus = digitalRead(doorSensorPin);

  if (currentStatus == LOW) {
    // Магнитите са ДОПРЕНИ -> Лампичката СВЕТИ
    digitalWrite(boardLED, HIGH);
  } else {
    // Магнитите са РАЗДЕЛЕНИ -> Лампичката СПИРА да свети
    digitalWrite(boardLED, LOW);
  }

  delay(50); // Малка пауза за стабилна работа
}
