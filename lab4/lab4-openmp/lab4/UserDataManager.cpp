#include "UserDataManager.h"

void UserDataManager::ConfigUserN()
{
	std::cout << "������ N (N >= 4) > ";
	std::cin >> N;
	if (N < MIN_N)
	{
		throw std::invalid_argument("N �� ���� ����� ��� �������� 4!");
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
	std::cout << "������� ����� ����� �����:\n1 - ������� � �����\n2 - ����������� ���������\n3 - ��������� ������\n> ";
	std::cin >> dataGeneration;
	std::string filePath;
	switch (dataGeneration)
	{
	case DataGenerationTypes::READ_FILE:
		std::cout << "������ ����� ����� > ";
		std::cin >> filePath;
		filePath = fr.ReadFileContents(filePath);
		break;
	case DataGenerationTypes::GENERATE_RANDOM:
		break;
	case DataGenerationTypes::FILL_WITH_NUMBER:
		std::cout << "������ ����� ��� ���������� �������� ����� > ";
		std::cin >> FILL_NUMBER;
		break;
	default:
		throw std::invalid_argument("������� ���� ��� ����� ������ ���������� �������� �����!");
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