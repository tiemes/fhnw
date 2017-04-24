#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void) {
    int p1[2];
    int p2[2];

    if (pipe(p1)) {
        perror("Unable to create the first pipe");
        return EXIT_FAILURE;
    }

    int pid;

    pid = fork();
    if(pid == 0) {
        dup2(p1[1], STDOUT_FILENO);
        close(p1[0]);
        execlp("/bin/cat", "/bin/cat", "/etc/passwd");//, "|", "grep", "root");
    } else {
        dup2(p1[0], STDIN_FILENO);
        close(p1[1]);

        if(pipe(p2)) {
            perror("Unable to create the first pipe");
            return EXIT_FAILURE;
        }

        pid = fork();
        if(pid == 0) {
            dup2(p2[1], STDOUT_FILENO);
            close(p2[0]);
            execl("/bin/grep", "/bin/grep");
        } else {

        }
    }
    return EXIT_SUCCESS;
}
