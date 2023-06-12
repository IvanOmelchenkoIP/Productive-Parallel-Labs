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
 * ����: Utils.h
 */

#pragma once

#include <string>
#include <iostream>

class DataGenerationTypes
{
public:
	static const int USER_INPUT = 0, RANDOM = 1, DEFAULT_NUMS = 2;
};

class DataConfigUtil
{
private:
	static const int MAX_SMALL_N_MULTIPLIER = 3;

	int MIN_N;
	int MAX_SMALL_N;

	int N = -1;

	int DATA_GENERATION = -1;

	void ConfigUserN();
	void ConfigInputType();

public:
	DataConfigUtil(int threads);

	void Config();

	int getDataGeneration();
	int getN();
};

class RandomUtil
{
public:
	int GenerateInRange(int min, int max);
};