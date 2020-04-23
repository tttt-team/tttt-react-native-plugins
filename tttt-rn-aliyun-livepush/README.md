# tttt-rn-aliyun-livepush

TTTT团队ReactNative阿里云流媒体服务插件

## Getting started

`$ npm install tttt-rn-aliyun-livepush --save`

### Mostly automatic installation

`$ react-native link tttt-rn-aliyun-livepush`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `tttt-rn-aliyun-livepush` and add `TtttRnAliyunLivepush.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libTtttRnAliyunLivepush.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import org.tttt.rn.aliyun.livepush.TtttRnAliyunLivepushPackage;` to the imports at the top of the file
  - Add `new TtttRnAliyunLivepushPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':tttt-rn-aliyun-livepush'
  	project(':tttt-rn-aliyun-livepush').projectDir = new File(rootProject.projectDir, 	'../node_modules/tttt-rn-aliyun-livepush/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':tttt-rn-aliyun-livepush')
  	```


## Usage
```javascript
import TtttRnAliyunLivepush from 'tttt-rn-aliyun-livepush';

// TODO: What to do with the module?
TtttRnAliyunLivepush;
```
