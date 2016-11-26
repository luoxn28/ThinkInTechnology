#include <iostream>
#include <queue>

#include <unistd.h>
#include <pthread.h>

using namespace std;


pthread_cond_t g_cond_notempty;
pthread_cond_t g_cond_notfull;
pthread_mutex_t g_mutex;

queue<int> g_queue;

/**
 * procudtor thread.
 */
void *func_productor(void *arg)
{
	int num = 0;

	while (1) {
		pthread_mutex_lock(&g_mutex);
		if (g_queue.size() >= 10) {
			pthread_cond_wait(&g_cond_notfull, &g_mutex);
		}
		g_queue.push(num);
		cout << "push " << num++ << endl;
		pthread_cond_signal(&g_cond_notempty);
		pthread_mutex_unlock(&g_mutex);

		//sleep(1);
	}
}

/**
 * customer thread.
 */
void *func_consumer(void *arg)
{
	while (1) {
		pthread_mutex_lock(&g_mutex);
		if (g_queue.size() == 0) {
			pthread_cond_wait(&g_cond_notempty, &g_mutex);
		}
		cout << "-----------get " << g_queue.front() << endl;
		g_queue.pop();
		pthread_cond_signal(&g_cond_notfull);
		pthread_mutex_unlock(&g_mutex);

		sleep(1);
	}
}

int main()
{
	pthread_t t1, t2;

	pthread_mutex_init(&g_mutex, NULL);
	pthread_cond_init(&g_cond_notempty, NULL);
	pthread_cond_init(&g_cond_notfull, NULL);

	pthread_create(&t1, NULL, func_productor, NULL);
	pthread_create(&t2, NULL, func_consumer, NULL);

	pthread_join(t1, NULL);
	pthread_join(t2, NULL);

	return 0;
}
