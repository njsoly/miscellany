
default_filename = 'numbers.csv'
lines = []

try:
    file1 = open(default_filename, 'r')
    lines = file1.readlines()
except FileNotFoundError:
    print("file {} doesn't exist".format(default_filename))
    exit(1)

if len(lines) == 0:
    print('no IDs found in "{}"'.format(default_filename))

count = 0
for line in lines:
    line = line.strip()
    if not line.isnumeric():
        print('line {} ("{}") is not numeric.  IDs must be numbers and one per line.'.format(count, line))
        exit(1)
    print("Line {}: {}".format(count, line.strip()))
    count += 1

