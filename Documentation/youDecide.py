import shutil
import subprocess
import os
import datetime
import time

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

TOMS_2_TEST_PROMOTION = "D:\\CMPT379\\input\\tan-2\Toms Test\\Promotion"
TOMS_2_TEST_PROMOTION_EXPECTED = "D:\\CMPT379\\input\\tan-2\\Toms Test Expected\\Promotion"

TOMS_2_TEST_ARRAY_CREATION = "D:\\CMPT379\\input\\tan-2\Toms Test\\ArrayCreation"
TOMS_2_TEST_ARRAY_CREATION_EXPECTED = "D:\\CMPT379\\input\\tan-2\\Toms Test Expected\\ArrayCreation"

TOMS_2_TEST_ARRAY_INDEX = "D:\\CMPT379\\input\\tan-2\\Toms Test\\ArrayIndex"
TOMS_2_TEST_ARRAY_INDEX_EXPECTED = "D:\\CMPT379\\input\\tan-2\\Toms Test Expected\\ArrayIndex"

TOMS_2_TEST_ARRAY_PRINT = "D:\\CMPT379\\input\\tan-2\\Toms Test\\ArrayPrint"
TOMS_2_TEST_ARRAY_PRINT_EXPECTED = "D:\\CMPT379\\input\\tan-2\\Toms Test Expected\\ArrayPrint"

TOMS_2_TEST_ARRAY_POPULATED = "D:\\CMPT379\\input\\tan-2\\Toms Test\\ArrayPopulated"
TOMS_2_TEST_ARRAY_POPULATED_EXPECTED = "D:\\CMPT379\\input\\tan-2\\Toms Test Expected\\ArrayPopulated"

TOMS_2_TEST_ARRAY_LENGTH = "D:\\CMPT379\\input\\tan-2\\Toms Test\\ArrayLength"
TOMS_2_TEST_ARRAY_LENGTH_EXPECTED = "D:\CMPT379\input\\tan-2\Toms Test Expected\AraryLength"

TOMS_2_TEST_ARRAY_SEMANTICS = "D:\\CMPT379\\input\\tan-2\\Toms Test\\ArraySemantics"
TOMS_2_TEST_ARRAY_SEMANTICS_EXPECTED = "D:\\CMPT379\\input\\tan-2\\Toms Test Expected\\ArraySemantics"

GENERAL_TEST_1 = "D:\CMPT379\input\\tan-1"
GENERAL_TEST_1_EXPECTED = "D:\CMPT379\input\\tan-1\expected"

GENERAL_TEST_2 = "D:\CMPT379\input\\tan-2"
GENERAL_TEST_2_EXPECTED = "D:\CMPT379\input\\tan-2\expected"

global TAN_PATH
global EXPECTED_PATH

OUTPUT_PATH_1 = "D:\CMPT379\input\\tan-1\output"
OUTPUT_PATH_2 = "D:\CMPT379\input\\tan-2\output"

ASM_PATH = "D:\CMPT379\ASM_Emulator\ASMEmu.exe"
BIN_PATH = "D:\CMPT379\\bin"

EXIT = "exit"

def run_java_file(java_file_path, java_class, file):
    command = ['javac', '-cp', "D:\\CMPT379\\src\\", '-d', BIN_PATH, "D:\\CMPT379\\src\\applications\\TanCompiler.java"]
    process = subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)

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
                if 'Violations' in entry.name or 'Conventions' in entry.name or 'err' in entry.name or 'rte' in entry.name or 'fff' in entry.name:
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
    good_file = []
    bad_file_names = []
    for i in range(len(files)):
        response = run_java_file(BIN_PATH, 'applications.TanCompiler', files[i])
        if response == False:
            bad_file.append(i)
            bad_file_names.append(files[i])
        else:
            good_file.append(files[i])
    return good_file, bad_file, bad_file_names


def ASM_file_execute_orchestrator(badFile):
    filesASM = find_files(OUTPUT_PATH)
    # for i in range(len(badFile)):
    #     filesASM.pop(badFile[i])
    return terminal_output_to_list(filesASM)


def expected_file_orchestrator(bad_file_names):
    expectedOutputs = []
    filesExpected = find_files(EXPECTED_PATH)
    filesExpected.sort()
    # for i in range(len(badFile)):
    #     filesExpected.pop(badFile[i])

    temp_set = {x.replace(".tan", ".txt") for x in bad_file_names}
    for i in range(len(filesExpected)):
        expectedOutputs.append(read_lines(f"{EXPECTED_PATH}\{filesExpected[i]}"))
    return expectedOutputs


def check_two_list(compilerOutput, expectedOutput):
    total = len(expectedOutput)
    iter = len(expectedOutput) if len(expectedOutput) < len(compilerOutput) else len(compilerOutput)
    counter = 0
    fail = []
    for i in range(iter):
        if any(check in compilerOutput[i] for check in expectedOutput):  # compilerOutput[i] == expectedOutput[i]:
            counter += 1
        else:
            fail.append(f"{compilerOutput[i]} | {expectedOutput[i]}")
    return counter, total, fail


def ticks(dt):
    return (dt - datetime(1, 1, 1)).total_seconds() * 10000000


def assertions(tanFiles, compilerOutput, expectedOutput, bad_file_names):
    test_not_ran = []
    for i in range(len(bad_file_names)):
        test_not_ran.append(bad_file_names[i])
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
    user_input = input("System: What Test to run? [Tom1, Tom2, General1, General2]\nYou: ")
    tan_path = ""
    expected_path = ""
    if user_input.lower() == "tom1":
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
        elif user_input.lower() == "typechecking":
            tan_path = TOMS_TEST_TYPECHECKING
            expected_path = TOMS_TEST_TYPECHECKING_EXPECTED
        else:
            return None, None
    elif user_input.lower() == "tom2":
        user_input = input("System: Please choose Test: [Promotion, ArrayLength, ArrayCreation, ArrayIndex, ArrayPopulated, ArraySemantics, ArrayPrint]\nYou: ")
        if user_input.lower() == "promotion":
            tan_path = TOMS_2_TEST_PROMOTION
            expected_path = TOMS_2_TEST_PROMOTION_EXPECTED
        elif user_input.lower() == "arraylength":
            tan_path = TOMS_2_TEST_ARRAY_LENGTH
            expected_path = TOMS_2_TEST_ARRAY_LENGTH_EXPECTED
        elif user_input.lower() == "arraycreation":
            tan_path = TOMS_2_TEST_ARRAY_CREATION
            expected_path = TOMS_2_TEST_ARRAY_CREATION_EXPECTED
        elif user_input.lower() == "arrayindex":
            tan_path = TOMS_2_TEST_ARRAY_INDEX
            expected_path = TOMS_2_TEST_ARRAY_INDEX_EXPECTED
        elif user_input.lower() == "arraypopulated":
            tan_path = TOMS_2_TEST_ARRAY_POPULATED
            expected_path = TOMS_2_TEST_ARRAY_POPULATED_EXPECTED
        elif user_input.lower() == "arraysemantics":
            tan_path = TOMS_2_TEST_ARRAY_SEMANTICS
            expected_path = TOMS_2_TEST_ARRAY_SEMANTICS_EXPECTED
        elif user_input.lower() == "arrayprint":
            tan_path = TOMS_2_TEST_ARRAY_PRINT
            expected_path = TOMS_2_TEST_ARRAY_PRINT_EXPECTED
        else:
            return None, None
    elif user_input.lower() == "general1":
        tan_path = GENERAL_TEST_1
        expected_path = GENERAL_TEST_1_EXPECTED
    elif user_input.lower() == "general2":
        tan_path = GENERAL_TEST_2
        expected_path = GENERAL_TEST_2_EXPECTED
    elif user_input.lower() == EXIT:
        tan_path = EXIT
        expected_path = EXIT
    else:
        return None, None
    return tan_path, expected_path


def deleteFolder():
    folder_path = OUTPUT_PATH
    if os.path.exists(folder_path):
        try:
            for filename in os.listdir(folder_path):
                file_path = os.path.join(folder_path, filename)
                if os.path.isfile(file_path):
                    os.unlink(file_path)
                elif os.path.isdir(file_path):
                    shutil.rmtree(file_path)
            print("Folder contents deleted successfully.")
        except OSError as e:
            print(f"Error: {e}. Failed to delete the folder contents.")


def setOutputPath():
    global OUTPUT_PATH
    if TAN_PATH == GENERAL_TEST_1:
        OUTPUT_PATH = OUTPUT_PATH_1
        return

    OUTPUT_PATH = OUTPUT_PATH_2


if __name__ == "__main__":
    while True:
        delete_if_exists("output.txt")
        TAN_PATH, EXPECTED_PATH = test_to_run()

        if TAN_PATH == None or EXPECTED_PATH == None:
            print("System: Invalid Input")
            continue

        if TAN_PATH == EXIT:
            break

        setOutputPath()
        deleteFolder()
        tanFiles, badFile, bad_file_names = java_file_execute_orchestrator()
        compilerOutput = ASM_file_execute_orchestrator(badFile)
        expectedOutput = expected_file_orchestrator(bad_file_names)
        assertions(tanFiles, compilerOutput, expectedOutput, bad_file_names)
