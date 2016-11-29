/**
 * Three three print a/b/c in order.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>

typedef struct ThreadInfo_t
{
	char info; /* 'A' or 'B' or 'C' */
	int  n;    /* remainder num */
	int  num;  /* share num */
	pthread_mutex_t mutex;
}ThreadInfo;

void *func(void *arg)
{
	int cnt = 3;
	ThreadInfo *info = (ThreadInfo *)arg;
	int  result = info->n;
	char show   = info->info;

	while (cnt > 0) {
		if (info->num % 3 == result) {
			printf("---%c\n", show);

			pthread_mutex_lock(&info->mutex);
			info->num++;
			cnt--;
			pthread_mutex_unlock(&info->mutex);
		}
	}

	return NULL;
}

int main(int argc, char **argv)
{
	pthread_t t1, t2, t3;
	ThreadInfo info;

	memset(&info, 0, sizeof(ThreadInfo));
	pthread_mutex_init(&(info.mutex), NULL);

	info.n = 0;
	info.info = 'A';
	pthread_create(&t1, NULL, func, &info);
	sleep(1);

	info.n = 1;
	info.info = 'B';
	pthread_create(&t2, NULL, func, &info);
	sleep(1);

	info.n = 2;
	info.info = 'C';
	pthread_create(&t3, NULL, func, &info);

	pthread_join(t1, NULL);
	pthread_join(t2, NULL);
	pthread_join(t3, NULL);

	return 0;
}
