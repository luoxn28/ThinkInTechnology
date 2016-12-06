#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <errno.h>

void *func(void *arg)
{
	printf("hello thread\n");

	return NULL;
}

int main()
{
	pthread_t tid;
	pthread_attr_t attr;

	pthread_attr_init(&attr);
	// 创建分离状态的线程
	pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);

	pthread_create(&tid, &attr, func, NULL);

	pthread_attr_destroy(&attr);

	sleep(1);

	return 0;
}
