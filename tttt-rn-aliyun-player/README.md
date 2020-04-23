# tttt-rn-aliyun-player

TTTT团队ReactNative阿里云播放器插件

## Getting started

`$ npm install tttt-rn-aliyun-player --save`

### Mostly automatic installation

`$ react-native link tttt-rn-aliyun-player`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `tttt-rn-aliyun-player` and add `TtttRnAliyunPlayer.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libTtttRnAliyunPlayer.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import org.tttt.rn.aliyun.player.TtttRnAliyunPlayerPackage;` to the imports at the top of the file
  - Add `new TtttRnAliyunPlayerPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':tttt-rn-aliyun-player'
  	project(':tttt-rn-aliyun-player').projectDir = new File(rootProject.projectDir, 	'../node_modules/tttt-rn-aliyun-player/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':tttt-rn-aliyun-player')
  	```


## Usage
```javascript
import TtttRnAliyunPlayer from 'tttt-rn-aliyun-player';

// TODO: What to do with the module?
TtttRnAliyunPlayer;
```
