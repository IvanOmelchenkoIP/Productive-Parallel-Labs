#pragma once
#include <iostream>
#include "Utils.h"
#include "DataCreators.h"

class UserDataManager
{
private:
	static const int MAX_SMALL_N_MULTIPLIER = 3;

	int MIN_VALUE;
	int MAX_VALUE;

	int MIN_N;
	int MAX_SMALL_N;

	int N = -1;

	int DATA_GENERATION = -1;

	void ConfigUserN();
	void ConfigInputType();
	void ConfigCreatorClasses();

public:
	UserDataManager(int threads, int min, int max);

	void Config();

	int getN();

	int CreateNumber(std::string name);
	int* CreateVector(std::string name);
	int** CreateMatrix(std::string name);
};

