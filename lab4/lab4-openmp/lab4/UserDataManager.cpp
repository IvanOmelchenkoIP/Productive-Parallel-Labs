#include "UserDataManager.h"

void UserDataManager::ConfigUserN()
{
	std::cout << "Введіть N (N >= 4) > ";
	std::cin >> N;
	if (N < MIN_N)
	{
		throw std::invalid_argument("N має бути більше або доравнює 4!");
	}
}

void UserDataManager::ConfigInputType()
{
	if (N <= MAX_SMALL_N)
	{
		DATA_GENERATION = DataGenerationTypes::USER_INPUT;
		return;
	}
		
	int dataGeneration;
	std::cout << "Виберіть спосіб вводу даних:\n1 - зчитати з файлу\n2 - згенерувати випадково\n3 - заповнити числом\n> ";
	std::cin >> dataGeneration;
	std::string filePath;
	switch (dataGeneration)
	{
	case DataGenerationTypes::READ_FILE:
		std::cout << "Введіть назву файлу > ";
		std::cin >> filePath;
		filePath = fr.ReadFileContents(filePath);
		break;
	case DataGenerationTypes::GENERATE_RANDOM:
		break;
	case DataGenerationTypes::FILL_WITH_NUMBER:
		std::cout << "Введіть число для заповнення структур даних > ";
		std::cin >> FILL_NUMBER;
		break;
	default:
		throw std::invalid_argument("Невірний вибір при виборі методу заповнення структур даних!");
	}
}

void UserDataManager::ConfigCreatorClasses()
{
	nc.Config(MIN_VALUE, MAX_VALUE, DATA_GENERATION, FILL_NUMBER, fileContents);
	vc.Config(MIN_VALUE, MAX_VALUE, DATA_GENERATION, FILL_NUMBER, N, fileContents);
	mc.Config(MIN_VALUE, MAX_VALUE, DATA_GENERATION, FILL_NUMBER, N, fileContents);
}

UserDataManager::UserDataManager(int threads, int min, int max)
{
	MIN_VALUE = min;
	MAX_VALUE = max;
	MIN_N = threads;
	MAX_SMALL_N = threads * MAX_SMALL_N_MULTIPLIER;
}

void UserDataManager::Config()
{
	ConfigUserN();
	ConfigInputType();
	ConfigCreatorClasses();
}

int UserDataManager::getN()
{
	return N;
}

int UserDataManager::CreateNumber(std::string name)
{
	return nc.Create(name);
}

int* UserDataManager::CreateVector(std::string name)
{
	return vc.Create(name);
}

int** UserDataManager::CreateMatrix(std::string name)
{
	return mc.Create(name);
}