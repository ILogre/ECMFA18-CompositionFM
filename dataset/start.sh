IMAGE=petitroll/i3s-catalogue:1.0

command -v docker > /dev/null
if [ "$?" -ne "0" ]; then
  echo "Error: requiring docker"
  exit 1
fi

docker run -v $PWD:/catalogue/host -it $IMAGE $@
