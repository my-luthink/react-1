/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */

import React, {
    AppRegistry,
    Component,
    StyleSheet,
    Text,
    Image,
    ToastAndroid,
    TouchableHighlight,
    View,
    Vibration,
    NativeModules
} from 'react-native';

 import TabBar from 'react-native-xtabbar';//主要框架
 var MyTextView = require('./myview/TextView');
 var MyImageView = require('./myview/ImageView');
 var Dimensions = require('Dimensions');
 var PixelRatio = require('PixelRatio');
 var MyQRView = require('./myview/QRView');
 var BarcodeScanner = require('./myview/BarcodeScanner');//扫描框
 var QRFromAlbum = require('./mymodule/QRFromAlbum');//选择本地图片结果
 var CreateQRImage = require('./myview/CreateQRMessage');//生成二维码图片
 var ImageChooserPackage = NativeModules.ImageChooserModule;
 var CameraResult = NativeModules.CameraResultModule;//扫描结果
 var ListViewExample = require('./myview/ListViewExample');//导入listview

class QRScan extends Component {
  constructor(props) {
     super(props);
     this.state = {
       flag:false,
       torchMode: 'off',
       cameraType: 'back',
       code:null,
       msg:null,
       result:null,
       type:null,
       imagepath:null,
       uri:null
     };
   }

  //扫描二维码结果回调函数
   barcodeReceived(e) {
//     this.setState({
//          flag:true,
//          code:null,
//          msg:'扫描成功',
//          result:e.data,
//          type:'相机',
//          imagepath:'null',
//          uri:null,
//       });
     console.log('Barcode: ' + e.data);
   //  console.log('Type: ' + e.type);
     CameraResult.showReuslt(e.data);
     Vibration.vibrate();//震动
     ToastAndroid.show(e.data,ToastAndroid.LONG);
   }
 //    async function getdata(){
 //    var data = await ImageChooserPackage.pickImage();
 //}
     onClick(){
      QRFromAlbum.chooseImage((res)=>{
          try {
              var json = JSON.parse(res);
              var path = json.imagepath;//得到图片的路径
              if(json.code==0){
                var data = json.data;
                this.setState({
                  code:json.code,
                  msg:json.msg,
                  result:data.result,
                  type:data.type,
                  imagepath:json.imagepath,
                  uri:"file://"+json.imagepath,
                 });
              }else{
                this.setState({
                   code:json.code,
                   msg:json.msg,
                   result:'null',
                   type:'null',
                   imagepath:'null',
                   uri:null,
                  });
              }
          }catch(err){
             console.log("err");
          }
          });
  }

//  firstPage(){
//  console.log("first onPress");
//     this.setState({
//       flag:false
//           });
//  }

// check(){
//   if(count==0){
//      count++;
//      ToastAndroid.show('false',ToastAndroid.LONG);
//    return false;
//   }else{
//     count--;
//     ToastAndroid.show('true',ToastAndroid.LONG);
//    return true;
//   }
// }

 render() {
          return (
               <TabBar style={styles.container}
               onItemSelected={(index) => {console.log(`current item index is ${index}`);}}
               >
                              <TabBar.Item
                                  icon={require('./image/start_normal.png')}
                                  selectedIcon={require('./image/start_hightlight.png')}
                                  onPress={() => {
                                      console.log("first onPress");
                                  }}
                                  title='首页'>
                                   <View style={styles.containerQRView}>
                                      <BarcodeScanner
                                       viewFinderBackgroundColor="#de6e6a6a"
                                       onBarCodeRead={this.barcodeReceived}
                                       style={styles.QRView}
                                       torchMode={this.state.torchMode}
                                       cameraType={this.state.cameraType}>
                                      </BarcodeScanner>
                                   </View>
                              </TabBar.Item>

                              <TabBar.Item
                                  icon={require('./image/start_normal.png')}
                                  selectedIcon={require('./image/start_hightlight.png')}
                                  onPress={() => {
                                       //console.log("first onPress");
                                       this.onClick();
 //                                      console.log(this.state.result);
                                  }}
                                  title='本地'>
                                  <View style={styles.container}>
                                  <Text style={{fontSize: 18}}>状态：{this.state.msg}</Text>
                                  <Text style={{fontSize: 18}}>类型：{this.state.type}</Text>
                                  <Text style={{fontSize: 18}}>图片位置：{this.state.imagepath}</Text>
                                  <Text style={{fontSize: 18}}>二维码内容：{this.state.result}</Text>
                                  <Image style={styles.ImageView}
                                         source={{uri:this.state.uri}}>
                                  </Image>
                                  </View>
                              </TabBar.Item>

                              <TabBar.Item
                                  icon={require('./image/start_normal.png')}
                                  selectedIcon={require('./image/start_hightlight.png')}
                                  onPress={()=>{console.log("create onPress");}}
                                  title='生成'>
                                  <View style={styles.container}>
                                    <CreateQRImage style={styles.container}>
                                    </CreateQRImage>
                                  </View>
                              </TabBar.Item>


                          </TabBar>
        );
     }
// }
 };

 var styles = StyleSheet.create({
     ImageView:{//图片view
         flex: 1,
     },
     containerQRView:{
         flex: 1,
         justifyContent: 'center',
         alignItems: 'stretch',
     },
     QRView:{//扫描框
          flex: 1,
          },
      container: {  //外层view
          flex: 1,
          },

 });
AppRegistry.registerComponent('QRScan', () => QRScan);
