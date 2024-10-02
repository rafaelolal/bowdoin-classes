#include <stdio.h>
#include <vector>
using namespace std;

int main(int argc, char **argv)
{
    if (argc > 1)
    {
        printf("Bye %s\n", argv[0]);
    }
    printf("Hello %s\n", argv[0]);

    vector<int> v;
    for (int i = 0; i < 10; i++)
    {
        v.push_back(i);
    }

    return 0;
}
