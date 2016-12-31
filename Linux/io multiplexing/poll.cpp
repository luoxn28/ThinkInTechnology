#include <iostream>
#include <vector>

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/poll.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>

using namespace std;

vector<pollfd> flipVector(vector<pollfd> &fds);
struct pollfd *getPollfd(vector<pollfd> &fds, int *ppoll_size);

int main(int argc, char **argv)
{
	int listenfd, connfd;
	struct sockaddr_in servaddr;
	
	listenfd = socket(AF_INET, SOCK_STREAM, 0);
	
	memset(&servaddr, 0, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_port = htons(8080);
	servaddr.sin_addr.s_addr = INADDR_ANY;
	
	bind(listenfd, (struct sockaddr *)&servaddr, sizeof(servaddr));
	listen(listenfd, 5);
	
	struct pollfd poll_fd;
	vector<struct pollfd> fds;
	
	poll_fd.fd = STDIN_FILENO;
	poll_fd.events = POLLIN;
	fds.push_back(poll_fd);
	
	poll_fd.fd = listenfd;
	poll_fd.events = POLLIN;
	fds.push_back(poll_fd);
	
	char buff[1024];
	struct pollfd *ppoll = NULL;
	int poll_size = 0;
	
	ppoll = getPollfd(fds, &poll_size);
	
	bool running = true;
	while (running) {
		int oldSize = fds.size();
		buff[0] = '\0';
		
		int event_num = poll(ppoll, poll_size, -1);
		if (event_num < 0) {
			cerr << "select error" << endl;
			break;
		}
		
		int fds_size = fds.size();
		for (int i = 0; i < fds_size; i++) {
			if (ppoll[i].fd == STDIN_FILENO) {
				// 从STDIN_FILENO中读取数据
				if (ppoll[i].revents & POLLIN) {
					cin >> buff;
					if (strcmp(buff, "quit") == 0) {
						running = false;
						break;
					}
					else {
						cout << buff << endl;
					}
				}
			}
			else if (ppoll[i].fd == listenfd) {
				if (ppoll[i].revents & POLLIN) {
					connfd = accept(listenfd, NULL, NULL);
					if (connfd < 0) {
						running = false;
						break;
					}
					
					poll_fd.fd = connfd;
					poll_fd.events = POLLIN;
					fds.push_back(poll_fd);
					cout << "往fds添加 " << connfd << ", fds.size: " << fds.size() << endl;
				}
			}
			else {
				if (ppoll[i].revents & POLLIN) {
					int len = recv(ppoll[i].fd, buff, sizeof(buff) - 1, 0);
					if (len < 0) {
						cerr << "recv error" << endl;
					}
					else if (len == 0) {
						cout << "从fds删除 " << fds[i].fd << endl;
						// 客户端断开了连接
						fds[i].events = 0;
						fds[i].fd = -1;
					}
					else {
						buff[len] = '\0';
						cout << fds[i].fd << " recv: " << buff << endl;
					}
				}
			}
		}
		
		fds = flipVector(fds);
		if (oldSize != fds.size()) {
			free(ppoll);
			ppoll = getPollfd(fds, &poll_size);
		}
	}
	
	// 关闭文件描述符
	for (int i = 0; i < fds.size(); i++) {
		if (fds[i].fd != -1) {
			close(fds[i].fd);
		}
	}
	close(listenfd);

	return 0;
}

struct pollfd *getPollfd(vector<pollfd> &fds, int *ppoll_size)
{
	struct pollfd *poll = (struct pollfd *) malloc(fds.size() * sizeof(struct pollfd));
	for (int i = 0; i < fds.size(); i++) {
		poll[i].fd = fds[i].fd;
		poll[i].events = fds[i].events;
	}
	
	*ppoll_size = fds.size();
	return poll;
}

vector<pollfd> flipVector(vector<pollfd> &fds) {
	vector<pollfd> fdsnew;
	
	for (int i = 0; i < fds.size(); i++) {
		if (fds[i].fd != -1) {
			fdsnew.push_back(fds[i]);
		}
	}
	
	return fdsnew;
}
