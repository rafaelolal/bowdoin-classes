// hello how are youd
int main() {
  bool a;
  char b;
  int c;
  float d;
  bool e, f, g, h, i, j, k;
  a = true;
  b = 'a';
  c = 10;
  d = 10.5;
  e = true;
  f = false;

  // yessir

  if (a + b / c * d) {
    if (e) {
      if (f) {
        if (g) {
          if (h) {
            if (i) {
              if (j) {
                if (k) {
                  a = false;
                }
              }
              if (k) {
                a = false;
              } else {
                a = true;
              }
            }
          }
        } else {
          a = false;
        }
      } else {
        a = true;
      }
    }
  }

  if (a) {
    b = 'b';
  } else {
    c = 20;
  }
  if (a) {
    b = 'b';
  }
  if (a) {
    b = 'b';
  } else {
    c = 20;
  }

  if (a <= b && c >= d) {
    a = true;
  } else {
    a = false;
  }

  // comment there
  if (a == b || c != d) { // hello there
    a = true;
  } else {
    a = false;
  }

  // comment here
  while (5 + 1 - 3 / 13 * 2 == d) {
    a = true;
  }

  if (a && b || c == d + 3 - 1 && d) {
    a = true;
  } else {
    a = false;
  }

  print a;
  return b;
}