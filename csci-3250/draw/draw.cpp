/* draw.cpp, Laura Toma
   OpenGL legacy 1.x

   Shows simple drawing of points and lines and polygons in the default 2D projection.


   command line arguments: n is the number of points.

  User can
   interact by pressing various keys.
*/

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <assert.h>
#include <strings.h>

// to compile on both apple and unix platform
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#include <vector>
using namespace std;

// pre-defined colors for convenience
GLfloat red[3] = {1.0, 0.0, 0.0};
GLfloat green[3] = {0.0, 1.0, 0.0};
GLfloat blue[3] = {0.0, 0.0, 1.0};
GLfloat black[3] = {0.0, 0.0, 0.0};
GLfloat white[3] = {1.0, 1.0, 1.0};
GLfloat gray[3] = {0.5, 0.5, 0.5};
GLfloat yellow[3] = {1.0, 1.0, 0.0};
GLfloat magenta[3] = {1.0, 0.0, 1.0};
GLfloat cyan[3] = {0.0, 1.0, 1.0};

// more colors from
/* from https://www.opengl.org/discussion_boards/showthread.php/132502-Color-tables  */
GLfloat brown[3] = {0.647059, 0.164706, 0.164706};
GLfloat DarkBrown[3] = {0.36, 0.25, 0.20};
GLfloat DarkTan[3] = {0.59, 0.41, 0.31};
GLfloat Maroon[3] = {0.556863, 0.137255, 0.419608};
GLfloat DarkWood[3] = {0.52, 0.37, 0.26};
GLfloat Copper[3] = {0.72, 0.45, 0.20};
GLfloat green1[3] = {.5, 1, 0.5};
GLfloat green2[3] = {0.0, .8, 0.0};
GLfloat green3[3] = {0.0, .5, 0.0};
GLfloat ForestGreen[3] = {0.137255, 0.556863, 0.137255};
GLfloat MediumForestGreen[3] = {0.419608, 0.556863, 0.137255};
GLfloat LimeGreen[3] = {0.196078, 0.8, 0.196078};
GLfloat Orange[3] = {1, .5, 0};
GLfloat Silver[3] = {0.90, 0.91, 0.98};
GLfloat Wheat[3] = {0.847059, 0.847059, 0.74902};

// window size for the graphics window
const int WINSIZE = 500;
bool polygonMode = true;
int polygonSize = 0;

// note: use integer coordinates, for convenience when generating
// random numbers
typedef struct _point2d
{
    int x, y;
} point2d;

// the points in local coordinate system  [0,WINSIZE]x[0,WINSIZE).
int NPOINTS;

/********************************************************************/
// forward declarations
void display(void);
void keypress(unsigned char key, int x, int y);

/* ****************************** */
int main(int argc, char **argv)
{

    // read number of points from user
    if (argc != 2)
    {
        printf("usage: viewPoints <nbPoints>\n");
        exit(1);
    }
    NPOINTS = atoi(argv[1]);
    printf("you entered n=%d\n", NPOINTS);
    assert(NPOINTS > 0);

    // start the rendering
    /* initialize GLUT  */
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize(WINSIZE, WINSIZE);
    glutInitWindowPosition(100, 100);
    glutCreateWindow(argv[0]);

    /* register callback functions */
    glutDisplayFunc(display);
    glutKeyboardFunc(keypress);

    /* init GL */
    /* set background color black*/
    glClearColor(0, 0, 0, 0);

    /* give control to event handler */
    glutMainLoop();
    return 0;
}

void draw_stuff()
{

    // set fill to be equal to polygonMode
    glPolygonMode(GL_FRONT_AND_BACK, polygonMode ? GL_FILL : GL_LINE);

    glBegin(GL_POLYGON);

    // draw triangle
    glColor3fv(red);
    glVertex2i(200, 400);
    glColor3fv(yellow);
    glVertex2i(200, 450);
    glColor3fv(blue);
    glVertex2i(100, 400);

    glEnd();

    // draw colorful square
    glBegin(GL_POLYGON);
    glColor3fv(red);
    glVertex2i(100, 100);
    glColor3fv(green);
    glVertex2i(100, 200);
    glColor3fv(blue);
    glVertex2i(200, 200);
    glColor3fv(yellow);
    glVertex2i(200, 100);
    glEnd();

    glBegin(GL_LINES);
    glColor3fv(green);
    glVertex2i(300, 100);
    glVertex2i(300, 200);
    glEnd();

    glBegin(GL_POLYGON);
    double step = 2 * M_PI / polygonSize;
    double rad = 40;
    for (int i = 0; i < polygonSize; i++)
    {
        double x = 300 + rad * cos(i * step);
        double y = 300 + rad * sin(i * step);
        glVertex2f(x, y);

        // x = 300 + rad / 2 * cos(i * step);
        // y = 300 + rad / 2 * sin(i * step);
        // glVertex2f(x, y);
    }
    glEnd();
}

/* ****************************** */
/* We registered this function as the "displayFunc --- it is called by
   openGL to render the window.


   here is where we draw what we want to show in the window
 */
void display(void)
{
    glClear(GL_COLOR_BUFFER_BIT);
    // clear all modeling transformations
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    /* The default GL window is [-1,1]x[-1,1]x[-1,1] with the origin in
       the center. The camera is at (0,0,0) looking down negative
       z-axis.

       Our local coordinate system will be [0,WINSIZE]x[0,WINSIZE) so we
       need to transform points in [0,WINSIZE]x[0,WINSIZE) to [-1,1]x
       [-1,1]
    */

    // Our points are in [0,WINSIZE]x[0,WINSIZE], we want to transform
    // this to GL coordinate system [-1,1]x[-1,1]

    // scale the points to [0,2]x[0,2]
    glScalef(2.0 / WINSIZE, 2.0 / WINSIZE, 1.0);
    // translate the points to bring them to [-WINSIZE/2, WINSIZE/2]x [-WINSIZE/2, WINSIZE/2]
    glTranslatef(-WINSIZE / 2, -WINSIZE / 2, 0);

    // draw our stuff
    draw_stuff();

    /* execute the drawing commands */
    glFlush();
}

/* ****************************** */
/* Handler for key presses. called whenever a key is pressed */
void keypress(unsigned char key, int x, int y)
{
    switch (key)
    {
    case 'q':
        exit(0);
        break;
    case 'f':
        polygonMode = !polygonMode;
        glutPostRedisplay();
        break;
    }

    // if key is integer from 1 to 9
    if (key >= '1' && key <= '9')
    {
        printf("%c", key);
        // get the integer value of the key
        int n = key - '0';
        //
        polygonSize = n;
        glutPostRedisplay();
    }
}
