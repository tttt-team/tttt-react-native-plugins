# tttt-rn-rong-im

TTTT团队ReactNative融云即时通讯插件

## Getting started

`$ npm install tttt-rn-rong-im --save`

### Mostly automatic installation

`$ react-native link tttt-rn-rong-im`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `tttt-rn-rong-im` and add `TtttRnRongIm.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libTtttRnRongIm.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import org.tttt.rn.rong.im.TtttRnRongImPackage;` to the imports at the top of the file
  - Add `new TtttRnRongImPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':tttt-rn-rong-im'
  	project(':tttt-rn-rong-im').projectDir = new File(rootProject.projectDir, 	'../node_modules/tttt-rn-rong-im/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':tttt-rn-rong-im')
  	```


## Usage
```javascript
import TtttRnRongIm from 'tttt-rn-rong-im';

// TODO: What to do with the module?
TtttRnRongIm;
```
