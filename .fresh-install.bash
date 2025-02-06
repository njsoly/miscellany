#!/usr/bin/env bash


cat ~/.bashrc | grep '~/miscellany/.bashrc' > /dev/null

if [[ "$?" = "0" ]]; then
	echo "miscellany is already sourced."
	exit 0
fi

cat <<EOF > base_bashrc.txt
if [ -f ~/miscellany/.bashrc ]; then
	. ~/miscellany/.bashrc
else
	echo "$WARNING: ~/miscellany/.bashrc not found."
	return 1
fi
EOF

echo "adding some lines to the end of .bashrc"
cat base_bashrc.txt >> ~/.bashrc

rm base_bashrc.txt

