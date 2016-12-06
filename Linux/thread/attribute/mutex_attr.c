#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <errno.h>

pthread_mutex_t g_mutex;

void *func(void *arg)
{
	pthread_mutex_lock(&g_mutex);
	pthread_mutex_lock(&g_mutex);
	printf("hello thread\n");
	pthread_mutex_unlock(&g_mutex);
	pthread_mutex_unlock(&g_mutex);

	return NULL;
}

int main()
{
	pthread_t tid;
	pthread_mutexattr_t attr;

	pthread_mutexattr_settype(&attr, PTHREAD_MUTEX_RECURSIVE);

	pthread_mutex_init(&g_mutex, &attr);

	pthread_create(&tid, NULL, func, NULL);

	pthread_join(tid, NULL);
	pthread_mutex_destroy(&g_mutex);

	return 0;
}
