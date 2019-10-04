#!/usr/bin/env bash
_wipe_gradle_locks () {
	find ~/.gradle -type f -name "*.lock" | while read f; do rm $f; done
}
