# Made by @iarkn

if [ $SHELL != "$PREFIX/bin/bash" ];
then
	echo "Incompatible shell. Only `bash` is supported for now."
	exit 1
fi

# Change to home directory
cd ~

# Get command-line tools for Android SDK
wget https://dl.google.com/android/repository/commandlinetools-linux-7302050_latest.zip
unzip commandlinetools-linux-*.zip -d android/

# Export android root environment variable
echo "export ANDROID_HOME=$HOME/android" >> ~/.bashrc
export ANDROID_HOME=$HOME/android
echo "Android home: $ANDROID_HOME"

# Install platforms and platform-tools
cd android/cmdline-tools/bin
./sdkmanager --sdk_root=$ANDROID_HOME "platforms;android-30" "platform-tools"

# Install build tools manually
cd ../../
wget https://dl.google.com/android/repository/build-tools_r30.0.3-linux.zip
# Unpack and move build tools
unzip build-tools_*-linux.zip
mkdir build-tools
mv android-11/ build-tools/30.0.3/

# Cleanup
rm ~/android/build-tools*.zip
rm ~/commandlinetools*.zip

# Export build tools binaries to PATH
echo "export PATH=$PATH:$ANDROID_SDK_ROOT/build-tools/30.0.3" >> ~/.bashrc

echo "Finished setting up Android SDK, please re-login (exit then reopen Termux) or do 'source ~/.bashrc'"
