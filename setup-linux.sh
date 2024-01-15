# Made by @iarkn, modified by @smolkeys_

# Change to home directory
cd ~

# Get command-line tools for Android SDK
wget https://dl.google.com/android/repository/commandlinetools-linux-7302050_latest.zip
unzip commandlinetools-linux-*.zip -d android/

# Export android root environment variable
export ANDROID_HOME=$HOME/android
echo "Android home: $ANDROID_HOME"

# Install platforms and platform-tools
cd android/cmdline-tools/bin
./sdkmanager --sdk_root=$ANDROID_HOME "platforms;android-30" "platform-tools"

# Install build tools manually
cd ../../
wget https://dl.google.com/android/repository/build-tools_r33-linux.zip
# Unpack and move build tools
unzip build-tools_*-linux.zip
mkdir build-tools
mv android-13/ build-tools/33.0.0/

export ANDROID_SDK_ROOT=$HOME/android/platforms/android-30
echo "Android SDK root: $ANDROID_SDK_ROOT"

echo "export ANDROID_HOME=$ANDROID_HOME" >> .bashrc
echo "export ANDROID_SDK_ROOT=$ANDROID_SDK_ROOT" >> .bashrc

# Cleanup
rm ~/android/build-tools*.zip
rm ~/commandlinetools*.zip

echo "Finished setting up Android SDK."
