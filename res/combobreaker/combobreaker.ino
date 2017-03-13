#define CW true
#define CCW !CW

#include <Servo.h>
#include <Encoder.h>

#define SERVO_DEFAULT 30
#define SERVO_MIN 0
#define SERVO_MAX 200
#define SERVO_PIN 9
#define SERVO_FEEDBACK_PIN A0
#define FB_DIFF 10
#define FB_TOLERANCE 3.5
#define FB_DELAY 500

#define ENCODER1 2
#define ENCODER2 3
#define ENCODER_STEPS 1200

#define DIR_PIN  5
#define STEP_PIN 6


#define STEPS 200

#define MIN_DELAY 10

#define DIGITS 40

#define mod(a, b) ((a % b + b) % b)

#define BAUDRATE 9600

int current_digit = 0;

Servo shackle;
Encoder myEnc(ENCODER1, ENCODER2);
long oldPosition;
int mini, maxi, minFeedback, maxFeedback;
char inputBuffer[10];

int combo[3];
String numbers[6];

void setup()
{
  Serial.begin(BAUDRATE);

  pinMode(DIR_PIN, OUTPUT);
  pinMode(STEP_PIN, OUTPUT);
  pinMode(SERVO_FEEDBACK_PIN, INPUT);
  shackle.attach(SERVO_PIN);
  oldPosition = myEnc.read();

  calibrateServo();
}

void blink(int num) {
  for (int i = 0; i < num; i++) {
    digitalWrite(LED_BUILTIN, HIGH);
    delay(500);
    digitalWrite(LED_BUILTIN, LOW);
    delay(500);
  }
}

void loop()
{
  if (Serial.available() > 0) {
    Serial.readBytes(inputBuffer, Serial.available());


    if (numbers[0].equals("")) {
      numbers[0] = inputBuffer;
    } else if (numbers[1].equals("")) {
      numbers[1] = inputBuffer;
    } else if (numbers[2].equals("")) {
      numbers[2] = inputBuffer;
    } else if (numbers[3].equals("")) {
      numbers[3] = inputBuffer;
    } else if (numbers[4].equals("")) {
      numbers[4] = inputBuffer;
    } else if (numbers[5].equals("")) {
      numbers[5] = inputBuffer;
    }

    if (!numbers[5].equals("")) {
      String res = "";
      for (int i = 0; i < 2; i++) {
        res += numbers[i];
      }


      combo[0] = res.toInt();

      res = "";
      for (int i = 2; i < 4; i++) {
        res += numbers[i];
      }

      combo[1] = res.toInt();

      res = "";
      for (int i = 4; i < 6; i++) {
        res += numbers[i];
      }

      combo[2] = res.toInt();

      pin(combo[0], combo[1], combo[2]);
      openShackle();
      for (int i = 0; i < 6; i++) {
//        blink(numbers[i].toInt());
//        delay(1000);
        numbers[i] = "";
      }
    }
  }
}

void shackleHigh()
{
  shackle.write(maxi + (FB_DIFF * (FB_TOLERANCE - 1)));
}

// puts the shackle in middle position
void shackleMid()
{
  shackle.write(maxi);
}

// attempts to open the shackle *safely*
boolean openShackle()
{
  int fb;
  boolean ret = false;

  shackle.write(maxi + (FB_DIFF * FB_TOLERANCE * 1.5));
  delay(300);

  fb = analogRead(SERVO_FEEDBACK_PIN);
  if (fb - maxFeedback > FB_DIFF * FB_TOLERANCE)
  {
    Serial.println("OPENED!");
    ret = true;
  }
  else
  {
    ret = false;
    Serial.println("not opened :(");
  }

  shackleMid();
  return ret;
}

// detect the middle and high positions of the servo
void calibrateServo()
{
  int fb;
  int i = SERVO_DEFAULT;

  shackle.write(i);
  delay(500);
  fb = analogRead(SERVO_FEEDBACK_PIN);

  Serial.println(fb);
  for (i -= FB_DIFF; i > SERVO_MIN; i -= FB_DIFF)
  {
    Serial.print("i=");
    Serial.print(i);
    shackle.write(i);
    mini = i;
    delay(FB_DELAY);
    minFeedback = analogRead(SERVO_FEEDBACK_PIN);
    Serial.print(" fb=");
    Serial.println(minFeedback);

    if (fb - minFeedback < FB_DIFF)
    {
      mini = i + (FB_DIFF * FB_TOLERANCE);
      Serial.println("crap!");
      break;
    }
    fb = minFeedback;

  }


  for (i += FB_DIFF * 2; i <= SERVO_MAX; i += FB_DIFF)
  {
    Serial.print("i=");
    Serial.print(i);
    shackle.write(i);
    maxi = i;
    delay(FB_DELAY);
    maxFeedback = analogRead(SERVO_FEEDBACK_PIN);
    Serial.print(" fb=");
    Serial.println(maxFeedback);

    if (maxFeedback - fb < FB_DIFF)
    {
      maxi = i - (FB_DIFF * FB_TOLERANCE);
      Serial.print("crap!! changing to ");
      Serial.println(maxi);
      shackleMid();
      break;
    }
    fb = maxFeedback;

  }

  Serial.print("mini=");
  Serial.print(mini);
  Serial.print(" maxi=");
  Serial.print(maxi);
  Serial.println("");
}

// enter a full combination through the dial
void pin(int pin1, int pin2, int pin3)
{
  spinto(3, pin1, CW);
  delay(1000);

  spinto(1, pin2, CCW);
  delay(1000);

  spinto(0, pin3, CW);
  delay(1000);
}

void spinto(int spins, int digit, boolean cw)
{
  digitalWrite(DIR_PIN, cw ? HIGH : LOW);

  for (int i = 0; i < spins; i++)
  {
    step(STEPS);
  }
  int tmp = digit;
  Serial.print("going to ");
  Serial.print(digit);
  Serial.print(" cur=");
  Serial.print(current_digit);

  if (cw == CW)
    digit = (DIGITS - digit) + current_digit;
  else
    digit = digit - current_digit;

  digit = mod(digit, DIGITS);

  // track where we are for next time
  current_digit = tmp;

  Serial.print(" moving ");
  Serial.println(digit);
  step(digit * (STEPS / DIGITS));
}
void step(int steps)
{
  long newPosition;
  for (int i = 0; i < steps; i++)
  {
    digitalWrite(STEP_PIN, LOW);
    digitalWrite(STEP_PIN, HIGH);
    delay(MIN_DELAY);
  }

  newPosition = myEnc.read();
  if (newPosition != oldPosition)
  {
    oldPosition = mod(newPosition, ENCODER_STEPS);//XXX
    Serial.print("newpos=");
    Serial.print(newPosition);
    Serial.print(" actual=");
    Serial.print((int)(oldPosition / DIGITS));
    Serial.print(" pos=");
    Serial.println(oldPosition);
  }
}

