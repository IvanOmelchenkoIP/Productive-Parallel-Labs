/*
 * Лабораторна робота 4 ЛР4, Варіант - 10
 * MR = MB*(MC*MM)*d + min(Z)*MC
 * Введенні і виведення даних:
 * T1: MB
 * T2: MR
 * T3: MC
 * T4: d, Z, MM
 * Омельченко І. ІП-04
 * Дата відправлення: 14.05.2023
 *
 * файл: lab4.cpp
 */

#include <iostream>
#include <windows.h>
#include <chrono>
#include <omp.h>
#include <thread>
#include <limits>

#include "Utils.h"

#define THREADS 4
#define MIN_VALUE -5000
#define MAX_VALUE 5000

#define DEFAULT_T1_VALUE 1
#define DEFAULT_T3_VALUE 3
#define DEFAULT_T4_VALUE 4

int main()
{
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    DataConfigUtil manager = DataConfigUtil(THREADS);
    manager.Config();
    const int N = manager.getN();

    std::chrono::high_resolution_clock::time_point start = std::chrono::high_resolution_clock::now();
    int q = MAX_VALUE;
    int d;
    int* Z = new int[N];
    int** MB = new int* [N];
    int** MC = new int* [N];
    int** MM = new int* [N];

    int** MR = new int* [N];
    int** MA = new int* [N];
    int** MQ = new int* [N];
    for (int i = 0; i < N; i++)
    {
        MB[i] = new int[N];
        MC[i] = new int[N];
        MM[i] = new int[N];
        MR[i] = new int[N];
        MA[i] = new int[N];
        MQ[i] = new int[N];
        for (int j = 0; j < N; j++)
        {
            MB[i][j] = 0; MC[i][j] = 0; MM[i][j] = 0;
            MR[i][j] = 0; MA[i][j] = 0; MQ[i][j] = 0;
        }
    }

    RandomUtil random = RandomUtil();
    omp_lock_t inputLock;
    omp_lock_t qLock;
    omp_lock_t dLock;
    omp_lock_t MCLock;
    omp_lock_t MBLock;
    omp_init_lock(&inputLock);
    omp_init_lock(&qLock);
    omp_init_lock(&dLock);
    omp_init_lock(&MCLock);
    omp_init_lock(&MBLock);

    omp_set_num_threads(4);
#pragma omp parallel shared(std::cin, q, MB, MC, MA)
    {
        int q1, d1;
        int** MC1 = new int* [N];
        int** MB1 = new int* [N];

        int i, j, k;
#pragma omp sections
        {
#pragma omp section
            {
                if (manager.getDataGeneration() == DataGenerationTypes::USER_INPUT)
                {
                    omp_set_lock(&inputLock);
                    std::cout << "Введення MB:\n";
                    for (i = 0; i < N; i++)
                    {
                        for (j = 0; j < N; j++)
                        {
                            std::cout << "Введіть mb" << i << j << ": ";
                            std::cin >> MB[i][j];
                        }
                    }
                    omp_unset_lock(&inputLock);
                }
                else
                {
                    if (manager.getDataGeneration() == DataGenerationTypes::RANDOM)
                    {
                        for (i = 0; i < N; i++)
                        {
                            for (j = 0; j < N; j++) MB[i][j] = random.GenerateInRange(MIN_VALUE, MAX_VALUE);
                        }
                    }
                    else if (manager.getDataGeneration() == DataGenerationTypes::DEFAULT_NUMS)
                    {
                        for (i = 0; i < N; i++)
                        {
                            for (j = 0; j < N; j++) MB[i][j] = DEFAULT_T1_VALUE;
                        }
                    }
                    else
                    {
                        std::cerr << "Помилковий тип вводу даних!\n";
                        exit(1);
                    }
                }
            }
#pragma omp section
            {}
#pragma omp section
            {
                if (manager.getDataGeneration() == DataGenerationTypes::USER_INPUT)
                {
                    omp_set_lock(&inputLock);
                    std::cout << "Введення MC:\n";
                    for (i = 0; i < N; i++)
                    {
                        for (j = 0; j < N; j++)
                        {
                            std::cout << "Введіть mc" << i << j << ": ";
                            std::cin >> MC[i][j];
                        }
                    }
                    omp_unset_lock(&inputLock);
                }
                else
                {
                    if (manager.getDataGeneration() == DataGenerationTypes::RANDOM)
                    {
                        for (i = 0; i < N; i++)
                        {
                            for (j = 0; j < N; j++) MC[i][j] = random.GenerateInRange(MIN_VALUE, MAX_VALUE);
                        }
                    }
                    else if (manager.getDataGeneration() == DataGenerationTypes::DEFAULT_NUMS)
                    {
                        for (i = 0; i < N; i++)
                        {
                            for (j = 0; j < N; j++) MC[i][j] = DEFAULT_T3_VALUE;
                        }
                    }
                    else
                    {
                        std::cerr << "Помилковий тип вводу даних!\n";
                        exit(1);
                    }
                }
            }
#pragma omp section
            {
                if (manager.getDataGeneration() == DataGenerationTypes::USER_INPUT)
                {
                    omp_set_lock(&inputLock);
                    std::cout << "Введення MM:\n";
                    for (i = 0; i < N; i++)
                    {
                        for (j = 0; j < N; j++)
                        {
                            std::cout << "Введіть mm" << i << j << ": ";
                            std::cin >> MM[i][j];
                        }
                    }
                    omp_unset_lock(&inputLock);
                    omp_set_lock(&inputLock);
                    std::cout << "Введення Z:\n";
                    for (i = 0; i < N; i++)
                    {
                        std::cout << "Введіть z" << i << ": ";
                        std::cin >> Z[i];

                    }
                    omp_unset_lock(&inputLock);
                    omp_set_lock(&inputLock);
                    std::cout << "Введіть d: ";
                    std::cin >> d;
                    omp_unset_lock(&inputLock);
                }
                else
                {
                    if (manager.getDataGeneration() == DataGenerationTypes::RANDOM)
                    {
                        d = random.GenerateInRange(MIN_VALUE, MAX_VALUE);
                        for (i = 0; i < N; i++)
                        {
                            Z[i] = random.GenerateInRange(MIN_VALUE, MAX_VALUE);
                            for (j = 0; j < N; j++) MM[i][j] = random.GenerateInRange(MIN_VALUE, MAX_VALUE);
                        }
                    }
                    else if (manager.getDataGeneration() == DataGenerationTypes::DEFAULT_NUMS)
                    {
                        d = DEFAULT_T4_VALUE;
                        for (i = 0; i < N; i++)
                        {
                            Z[i] = DEFAULT_T4_VALUE;
                            for (j = 0; j < N; j++) MM[i][j] = DEFAULT_T4_VALUE;
                        }
                    }
                    else
                    {
                        std::cerr << "Помилковий тип вводу даних!\n";
                        exit(1);
                    }
                }
            }
        }

#pragma omp barrier

        q1 = MAX_VALUE;
#pragma omp for
        for (i = 0; i < N; i++)
        {
            if (Z[i] < q1) q1 = Z[i];
        }
#pragma omp critical 
        {
            q = q > q1 ? q1 : q;
        }

#pragma omp barrier

        omp_set_lock(&MCLock);
        for (i = 0; i < N; i++)
        {
            MC1[i] = new int[N];
            for (j = 0; j < N; j++) MC1[i][j] = MC[i][j];
        }
        omp_unset_lock(&MCLock);
#pragma omp parallel for
        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                for (k = 0; k < N; k++)
                {
                    MA[k][j] += MC1[i][k] * MB[k][j];
                }
            }
        }

#pragma omp barrier

        omp_set_lock(&MBLock);
        for (i = 0; i < N; i++)
        {
            MB1[i] = new int[N];
            for (j = 0; j < N; j++) MB1[i][j] = MB[i][j];
        }
        omp_unset_lock(&MBLock);
#pragma omp parallel for
        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                for (k = 0; k < N; k++)
                {
                    MQ[k][j] += MB1[i][k] * MA[k][j];
                }
            }
        }

#pragma omp barrier

        omp_set_lock(&dLock);
        d1 = d;
        omp_unset_lock(&dLock);
        omp_set_lock(&dLock);
        q1 = q;
        omp_unset_lock(&dLock);
#pragma omp parallel for private(k)
        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                MR[i][j] = MQ[i][j] * d1 + MC[i][j] * q1;
            }
        }

#pragma omp barrier

#pragma omp sections
        {
#pragma omp section
            { }
#pragma omp section
            {
                std::string result;
                std::cout << "Результат обчислення MR: \n";
                for (i = 0; i < N; i++)
                {
                    for (j = 0; j < N; j++) result.append(std::to_string(MR[i][j]) + ' ');
                    result.append("\n");
                }
                std::cout << "Результат обчислення MR: \n" << result;
            }
#pragma omp section
            { }
#pragma omp section
            { }
        }
    }
    std::chrono::high_resolution_clock::time_point end = std::chrono::high_resolution_clock::now();
    std::chrono::milliseconds ms = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);
    std::cout << "Час виконання: " << ms.count() << " мс\n";
}