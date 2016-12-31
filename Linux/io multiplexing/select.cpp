#include <iostream>
#include <vector>

#include <unistd.h>
#include <sys/select.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>

using namespace std;

int getMaxNumOfVector(vector<int> &fds);
vector<int> flipVector(vector<int> &fds);

int main(int argc, char **argv)
{
	vector<int> fds;
	int listenfd, connfd;
	struct sockaddr_in servaddr;
	
	listenfd = socket(AF_INET, SOCK_STREAM, 0);
	
	memset(&servaddr, 0, sizeof(servaddr));
	servaddr.sin_family = AF_INET;
	servaddr.sin_port = htons(8080);
	servaddr.sin_addr.s_addr = INADDR_ANY;
	
	bind(listenfd, (struct sockaddr *)&servaddr, sizeof(servaddr));
	listen(listenfd, 5);
	
	fd_set read_fd;
	fd_set write_fd;
	fd_set except_fd;
	char buff[1024];
	
	FD_ZERO(&read_fd);
	FD_ZERO(&write_fd);
	FD_ZERO(&except_fd);
	
	fds.push_back(STDIN_FILENO);
	fds.push_back(listenfd);
	
	bool running = true;
	while (running) {
		buff[0] = '\0';
		
		/**
		 * 每次调用select都要重新初始化read_fd和except_fd中的文件描述符集
		 */
		for (int i = 0; i < fds.size(); i++) {
			FD_SET(fds[i], &read_fd);
			if ((fds[i] != STDIN_FILENO) && (fds[i] != listenfd)) {
				//FD_SET(fds[i], &write_fd);
				FD_SET(fds[i], &except_fd);
			}
		}
		
		int event_num = select(getMaxNumOfVector(fds) + 1, &read_fd, &write_fd, &except_fd, NULL);
		if (event_num < 0) {
			cerr << "select error" << endl;
			break;
		}
		
		for (int i = 0; i < fds.size(); i++) {
			if (fds[i] == STDIN_FILENO) {
				// 从STDIN_FILENO中读取数据
				if (FD_ISSET(STDIN_FILENO, &read_fd)) {
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
			else if (fds[i] == listenfd) {
				if (FD_ISSET(listenfd, &read_fd)) {
					connfd = accept(listenfd, NULL, NULL);
					if (connfd < 0) {
						running = false;
						break;
					}
					
					fds.push_back(connfd);
					cout << "往fds添加 " << connfd << ", fds.size: " << fds.size() << endl;
				}
			}
			else {
				if (FD_ISSET(fds[i], &read_fd)) {
					int len = recv(fds[i], buff, sizeof(buff) - 1, 0);
					if (len < 0) {
						cerr << "recv error" << endl;
					}
					else if (len == 0) {
						cout << "从fds删除 " << fds[i] << endl;
						// 客户端断开了连接
						fds[i] = -1;
					}
					else {
						buff[len] = '\0';
						cout << fds[i] << " recv: " << buff << endl;
					}
				}
				else if (FD_ISSET(fds[i], &write_fd)) {
					
				}
				else if (FD_ISSET(fds[i], &except_fd)) {
					
				}
			}
		}
		
		fds = flipVector(fds);
	}
	
	// 关闭文件描述符
	for (int i = 0; i < fds.size(); i++) {
		close(fds[i]);
	}
	close(listenfd);

	return 0;
}

int getMaxNumOfVector(vector<int> &fds)
{
	int result = 0;
	
	for (int i = 0; i < fds.size(); i++) {
		if (fds[i] > result) {
			result = fds[i];
		}
	}
	
	return result;
}

vector<int> flipVector(vector<int> &fds) {
	vector<int> fdsnew;
	
	for (int i = 0; i < fds.size(); i++) {
		if (fds[i] != -1) {
			fdsnew.push_back(fds[i]);
		}
	}
	
	return fdsnew;
}