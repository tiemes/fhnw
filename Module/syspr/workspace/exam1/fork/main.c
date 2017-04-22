#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define MAX_COUNT  50

void handleChild(void) {
    int i;
    for (i = 1; i <= MAX_COUNT; i++) {
        printf("This line is from child, value = %d\n", i);
    }
    printf("*** Child process is done ***\n");
}

void handleParent(void) {
    int i;
    for (i = 1; i <= MAX_COUNT; i++) {
        printf("This line is from parent, value = %d\n", i);
    }
    printf("*** Parent is done ***\n");
}

int main(void) {
    int pid = fork();
    if (pid == -1) {
        perror("Unable to fork the process");
        return EXIT_FAILURE;
    }

    printf("Fork ID: %d | Process ID: %d | Parent process ID: %d\n", pid, getpid(), getppid());
    if (pid == 0) {
        handleChild();
    } else {
        handleParent();
    }
    return EXIT_SUCCESS;
}
