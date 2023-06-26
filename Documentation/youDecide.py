import shutil
import subprocess
import os
import datetime

TOMS_TEST_LEXICAL = "D:\CMPT379\input\\tan-1\\toms tests\lexical"
TOMS_TEST_LEXICAL_EXPECTED = "D:\CMPT379\input\\tan-1\Toms Test Expected\lexical"

TOMS_TEST_MISCELLANEOUS = "D:\CMPT379\input\\tan-1\\toms tests\miscellaneous"
TOMS_TEST_MISCELLANEOUS_EXPECTED = "D:\CMPT379\input\\tan-1\Toms Test Expected\miscellaneous"

TOMS_TEST_PRECEDENCE = "D:\CMPT379\input\\tan-1\\toms tests\precedence"
TOMS_TEST_PRECEDENCE_EXPECTED = "D:\CMPT379\input\\tan-1\Toms Test Expected\precedence"

TOMS_TEST_STATEMENTS = "D:\CMPT379\input\\tan-1\\toms tests\statements"
TOMS_TEST_STATEMENTS_EXPECTED = "D:\CMPT379\input\\tan-1\Toms Test Expected\statements"

TOMS_TEST_TYPECHECKING = "D:\CMPT379\input\\tan-1\\toms tests\\typechecking"
TOMS_TEST_TYPECHECKING_EXPECTED = "D:\CMPT379\input\\tan-1\Toms Test Expected\\typechecking"

GENERAL_TEST = "D:\CMPT379\input\\tan-1"
GENERAL_TEST_EXPECTED = "D:\CMPT379\input\\tan-1\expected"

global TAN_PATH
global EXPECTED_PATH

OUTPUT_PATH = "D:\CMPT379\input\\tan-1\output"
ASM_PATH = "D:\CMPT379\ASM_Emulator\ASMEmu.exe"
BIN_PATH = "D:\CMPT379\\bin"


def run_java_file(java_file_path, java_class, file):
    command = ['java', '-cp', java_file_path, java_class, f"{TAN_PATH}\{file}", OUTPUT_PATH]
    process = subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if process.stderr != b'':
        return False
    return True


def run_ASM_files(exe_path, arg):
    result = subprocess.run([exe_path] + [f'{OUTPUT_PATH}\{arg}'], capture_output=True, text=True)
    return result.stdout


def delete_if_exists(file_path):
    if os.path.exists(file_path):
        os.remove(file_path)


def find_files(directory_path):
    fileList = []
    with os.scandir(directory_path) as entries:
        for entry in entries:
            if entry.is_file():
                if 'Violations' in entry.name or 'Conventions' in entry.name or 'err' in entry.name or 'rte' in entry.name:
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
        result = run_ASM_files(ASM_PATH, filesASM[i])
        with open('output.txt', 'w') as file:
            for line in result:
                file.write(line)
        results.append(read_lines("output.txt"))
    delete_if_exists("output.txt")
    return results


def java_file_execute_orchestrator():
    files = find_files(TAN_PATH)
    bad_file = []
    for i in range(len(files)):
        response = run_java_file(BIN_PATH, 'applications.TanCompiler', files[i])
        if response == False:
            bad_file.append(i)
    for i in range(len(bad_file)):
        files.pop(bad_file[i])
    return files, bad_file


def ASM_file_execute_orchestrator():
    filesASM = find_files(OUTPUT_PATH)
    return terminal_output_to_list(filesASM)


def expected_file_orchestrator(badFile):
    expectedOutputs = []
    filesExpected = find_files(EXPECTED_PATH)
    for i in range(len(badFile)):
        filesExpected.pop(badFile[i])
    for i in range(len(filesExpected)):
        expectedOutputs.append(read_lines(f"{EXPECTED_PATH}\{filesExpected[i]}"))
    return expectedOutputs


def check_two_list(compilerOutput, expectedOutput):
    total = len(expectedOutput)
    counter = 0
    fail = []
    for i in range(total):
        if compilerOutput[i] == expectedOutput[i]:
            counter += 1
        else:
            fail.append(f"{compilerOutput[i]} | {expectedOutput[i]}")
    return counter, total, fail


def ticks(dt):
    return (dt - datetime(1, 1, 1)).total_seconds() * 10000000


def assertions(tanFiles, compilerOutput, expectedOutput):
    test_not_ran = []
    temp = ''
    for i in range(len(tanFiles)):
        try:
            common, total, fail = check_two_list(compilerOutput[i], expectedOutput[i])
            temp = temp + f'{tanFiles[i]}: {common}/{total}'
            if (len(fail)) > 0:
                temp = temp + f' <<<------------->>> {fail}\n'
            else:
                temp = temp + '\n'
        except:
            test_not_ran.append(tanFiles[i])
        with open(
                f"{datetime.datetime.now().year}-{datetime.datetime.now().month}-{datetime.datetime.now().day}_{datetime.datetime.now().hour}-{datetime.datetime.now().minute}-{datetime.datetime.now().second}.txt",
                'w') as file:
            for line in temp:
                file.write(line)
    print(temp)
    print(f"Couldnt Run Test on :{test_not_ran}")


def test_to_run():
    user_input = input("System: What Test to run? [Tom, General]\nYou: ")
    tan_path = ""
    expected_path = ""
    if user_input.lower() == "tom":
        user_input = input("System: Please choose Test: [Lexical, Miscellaneous, Precedence, Statements, TypeChecking]\nYou: ")
        if user_input.lower() == "lexical":
            tan_path = TOMS_TEST_LEXICAL
            expected_path = TOMS_TEST_LEXICAL_EXPECTED
        elif user_input.lower() == "miscellaneous":
            tan_path = TOMS_TEST_MISCELLANEOUS
            expected_path = TOMS_TEST_MISCELLANEOUS_EXPECTED
        elif user_input.lower() == "precedence":
            tan_path = TOMS_TEST_PRECEDENCE
            expected_path = TOMS_TEST_PRECEDENCE_EXPECTED
        elif user_input.lower() == "statements":
            tan_path = TOMS_TEST_STATEMENTS
            expected_path = TOMS_TEST_STATEMENTS_EXPECTED
        elif user_input.lower() == "typeChecking":
            tan_path = TOMS_TEST_TYPECHECKING
            expected_path = TOMS_TEST_TYPECHECKING_EXPECTED
        else:
            return None, None
    elif user_input.lower() == "general":
        tan_path = GENERAL_TEST
        expected_path = GENERAL_TEST_EXPECTED
    else:
        return None, None
    return tan_path, expected_path

if __name__ == "__main__":
    while True:
        delete_if_exists("output.txt")
        TAN_PATH, EXPECTED_PATH = test_to_run()
        if TAN_PATH == None or EXPECTED_PATH == None:
            print("System: Invalid Input exiting Program")
            exit(0)
        try:
            shutil.rmtree('D:\CMPT379\input\\tan-1\output\\')
        except:
            pass
        tanFiles, badFile = java_file_execute_orchestrator()
        compilerOutput = ASM_file_execute_orchestrator()
        expectedOutput = expected_file_orchestrator(badFile)
        assertions(tanFiles, compilerOutput, expectedOutput)
