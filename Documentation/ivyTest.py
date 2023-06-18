import subprocess
import os
import datetime
import time


def run_java_file(java_file_path, java_class, file):
    command = ['java', '-cp', java_file_path, java_class, f'C:\CMPT379\input\\tan-1\{file}', 'C:\CMPT379\input\\tan-1\output']
    process = subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)


def run_ASM_files(exe_path, arg):
    result = subprocess.run([exe_path] + [f'C:\CMPT379\input\\tan-1\output\{arg}'], capture_output=True, text=True)
    return result.stdout


def delete_if_exists(file_path):
    if os.path.exists(file_path):
        os.remove(file_path)


def find_files(directory_path):
    fileList = []
    with os.scandir(directory_path) as entries:
        for entry in entries:
            if entry.is_file():
                if 'Violations' in entry.name:
                    continue
                else:
                    fileList.append(entry.name)
    return fileList


def read_lines(textFile):
    with open(textFile, 'r') as file:
        lines = file.readlines()
    return lines


def terminal_output_to_list(filesASM):
    results = []
    for i in range(len(filesASM)):
        result = run_ASM_files('C:\CMPT379\ASM_Emulator\ASMEmu.exe', filesASM[i])
        with open('output.txt', 'w') as file:
            for line in result:
                file.write(line)
        results.append(read_lines("output.txt"))
    delete_if_exists("output.txt")
    return results


def java_file_execute_orchestrator():
    files = find_files('C:\CMPT379\input\\tan-1')
    for i in range(len(files)):
        run_java_file('C:\CMPT379\\bin', 'applications.TanCompiler', files[i])
    return files


def ASM_file_execute_orchestrator():
    filesASM = find_files('C:\CMPT379\input\\tan-1\output')
    return terminal_output_to_list(filesASM)


def expected_file_orchestrator():
    expectedOutputs = []
    filesExpected = find_files('C:\CMPT379\input\\tan-1\expected')
    for i in range(len(filesExpected)):
        expectedOutputs.append(read_lines(f'C:\CMPT379\input\\tan-1\expected\{filesExpected[i]}'))
    return expectedOutputs


def check_two_list(compilerOutput, expectedOutput):
    total = len(expectedOutput)
    counter = 0
    fail = []
    for i in range(total):
        if compilerOutput[i] == expectedOutput[i]:
            counter += 1
        else:
            fail.append(f"{counter+1} | {compilerOutput[i]} | {expectedOutput[i]}")
    return counter, total, fail


def ticks(dt):
    return (dt - datetime(1, 1, 1)).total_seconds() * 10000000


def assertions(tanFiles, compilerOutput, expectedOutput):
    temp = ''
    for i in range(len(tanFiles)):
        common, total, fail = check_two_list(compilerOutput[i], expectedOutput[i])
        temp = temp + f'{tanFiles[i]}: {common}/{total}'
        if (len(fail)) > 0:
            temp = temp + f' <<<------------->>> {fail}\n'
        else:
            temp = temp + '\n'
    with open(
            f"{datetime.datetime.now().year}-{datetime.datetime.now().month}-{datetime.datetime.now().day}_{datetime.datetime.now().hour}-{datetime.datetime.now().minute}-{datetime.datetime.now().second}.txt",
            'w') as file:
        for line in temp:
            file.write(line)
    print(temp)


if __name__ == "__main__":
    delete_if_exists("output.txt")
    tanFiles = java_file_execute_orchestrator()

    compilerOutput = ASM_file_execute_orchestrator()
    expectedOutput = expected_file_orchestrator()
    assertions(tanFiles, compilerOutput, expectedOutput)
