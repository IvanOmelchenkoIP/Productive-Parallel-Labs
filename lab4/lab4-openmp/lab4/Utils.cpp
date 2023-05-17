/*
 * ����������� ������ 4 ��4, ������ - 10
 * MR = MB*(MC*MM)*d + min(Z)*MC
 * ������� � ��������� �����:
 * T1: MB
 * T2: MR
 * T3: MC
 * T4: d, Z, MM
 * ���������� �. ��-04
 * ���� �����������: 14.05.2023
 *
 * ����: Utils.cpp
 */

#include "Utils.h"
#include <random>
#include <sstream>
#include <string.h>

void DataConfigUtil::ConfigUserN()
{
	std::cout << "������ N (N >= 4) > ";
	std::cin >> N;
	if (N < MIN_N)
	{
		std::cerr << "N �� ���� ����� ��� �������� 4!";
		exit(1);
	}
}

void DataConfigUtil::ConfigInputType()
{
	if (N <= MAX_SMALL_N)
	{
		DATA_GENERATION = DataGenerationTypes::USER_INPUT;
		return;
	}

	std::cout << "������� ����� ����� �����:\n1 - ����������� ���������\n2 - ��������� ������� �� ������������� (T1 - 1, T3 - 3, T4 - 4)\n> ";
	std::cin >> DATA_GENERATION;
	switch (DATA_GENERATION)
	{
	case DataGenerationTypes::RANDOM:
		break;
	case DataGenerationTypes::DEFAULT_NUMS:
		break;
	default:
		std::cerr << "������� ���� ��� ����� ������ ���������� �������� �����!";
		exit(1);
	}
}

DataConfigUtil::DataConfigUtil(int threads)
{
	MIN_N = threads;
	MAX_SMALL_N = threads * MAX_SMALL_N_MULTIPLIER;
}

int DataConfigUtil::getN()
{
	return N;
}

int DataConfigUtil::getDataGeneration() 
{
	return DATA_GENERATION;
}

void DataConfigUtil::Config()
{
	ConfigUserN();
	ConfigInputType();
}

int RandomUtil::GenerateInRange(int min, int max)
{
	std::random_device seed;
	std::default_random_engine generator(seed());
	std::uniform_int_distribution<int> distribution(min, max);
	return distribution(generator);
}