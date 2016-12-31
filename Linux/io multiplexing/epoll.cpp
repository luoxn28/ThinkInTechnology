#include <iostream>

#include <stdio.h>  
#include <stdlib.h>  
#include <unistd.h>  
#include <sys/types.h>  
#include <sys/socket.h>  
#include <sys/epoll.h>  
#include <netinet/in.h>  
#include <arpa/inet.h>  
#include <fcntl.h>  
#include <string.h> 

using namespace std;

void addfd(int epollfd, int fd)
{
	epoll_event event;
	
	event.data.fd = fd;
	event.events = EPOLLIN;
	epoll_ctl(epollfd, EPOLL_CTL_ADD, fd, &event);
}

void delfd(int epollfd, int fd)
{
	epoll_event event;
	
	event.data.fd = fd;
	event.events = EPOLLIN;
	epoll_ctl(epollfd, EPOLL_CTL_DEL, fd, &event);
}

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
	
	int epollfd = epoll_create(32);
	if (epollfd < 0) {
		cerr << "epoll_create error" << endl;
		exit(-1);
	}
	
	addfd(epollfd, STDIN_FILENO);
	addfd(epollfd, listenfd);
	
	epoll_event events[32];
	
	char buff[1024];
	bool running = true;
	while (running) {
		buff[0] = '\0';
		
		int event_num = epoll_wait(epollfd, events, 32, -1);
		if (event_num < 0) {
			cerr << "epoll_wait error" << endl;
			break;
		}
		
		for (int i = 0; i < event_num; i++) {
			int fd = events[i].data.fd;
			int event = events[i].events;
			
			if (fd == STDIN_FILENO) {
				// 从STDIN_FILENO中读取数据
				if (event & EPOLLIN) {
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
			else if (fd == listenfd) {
				if (event & EPOLLIN) {
					connfd = accept(listenfd, NULL, NULL);
					if (connfd < 0) {
						running = false;
						break;
					}
					
					addfd(epollfd, connfd);
					cout << "往epoll添加 " << connfd << endl;
				}
			}
			else {
				if (event & EPOLLIN) {
					int len = recv(fd, buff, sizeof(buff) - 1, 0);
					if (len < 0) {
						cerr << "recv error" << endl;
					}
					else if (len == 0) {
						cout << "从epoll删除 " << fd << endl;
						// 客户端断开了连接
						delfd(epollfd, fd);
					}
					else {
						buff[len] = '\0';
						cout << fd << " recv: " << buff << endl;
					}
				}
			}
		}
	}
	
	// 关闭文件描述符
	close(listenfd);

	return 0;
}
