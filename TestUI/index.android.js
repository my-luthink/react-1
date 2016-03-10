/**
 * index.android.js
 */
 'use strict';
 import React, {
   AppRegistry,
   Component,
   StyleSheet,
   Text,
     Image,
   TouchableHighlight,
   View,
     NativeModules
 } from 'react-native';
 var MyTextView = require('./myview/TextView');
 var MyImageView = require('./myview/ImageView');
 var path = require('./Lighthouse.jpg');
 var Dimensions = require('Dimensions');
 var PixelRatio = require('PixelRatio');
 var MyQRView = require('./myview/QRView');
 var BarcodeScanner = require('./myview/BarcodeScanner');
 var  QRFromAlbum = require('./mymodule/QRFromAlbum');
var ImageChooserPackage = NativeModules.ImageChooserModule;


 class TestQRAndroidExample extends React.Component{
 constructor(props) {
     super(props);
     this.state = {
       torchMode: 'off',
       cameraType: 'back',
         flag:false,
         imagePath:null,
     };
   }

     //扫描二维码结果回调函数
   barcodeReceived(e) {
     console.log('Barcode: ' + e.data);
     console.log('Type: ' + e.type);
   }
 //    async function getdata(){
 //    var data = await ImageChooserPackage.pickImage();
 //}
     onClick(){
      QRFromAlbum.chooseImage((res)=>{
          try {
              var json = JSON.parse(res);
              var path = json.imagepath;//得到图片的路径
              if(path!=null){
                  this.setState({
                      flag:true,
                      imagePath:path,
                  });
                  setTimeout(() => {
                      this.setState({
                          flag:false,
                          imagePath: null
                      });
                  }, 1000);
              }
          }catch(err){
              console.log(err);
          }
          });
  }

 render() {
     if(this.state.flag){
         console.log("flag true");
         console.log("path:"+this.state.imagePath);
         return (<View style={styles.main}>
                 <Image style={styles.containerImageView}
                        source={{uri:this.state.imagePath}}>
                 </Image>
         </View>
         );
     }else{
         console.log("flag false");
         return (<View style={styles.main}>
             <BarcodeScanner
                 viewFinderBackgroundColor="#de6e6a6a"
                 onBarCodeRead={this.barcodeReceived}
                 style={styles.containerQRView}
                 torchMode={this.state.torchMode}
                 cameraType={this.state.cameraType}>
             </BarcodeScanner>
             <Text style={styles.text}> 请将取景框对准二维码
             </Text>
             <TouchableHighlight style={styles.button} underlayColor='#99d9fe' onPress={()=>{this.onClick();}}>
                 <Text style={styles.buttontext}>打开相册中的二维码 </Text>
             </TouchableHighlight>
         </View>
       );
     }
 }
 };

 var styles = StyleSheet.create({
     containerTextView: {
        flex: 1,
   },
   containerImageView:{
         flex: 1,
         position:'relative',
       width:360,
       height:590,
   },
      containerQRView:{
          flex: 1,
          width:360,
          height:590,
      },
     text:{
           position:'relative',
           color:'white',
           flex:1,
           bottom:160,
           left:90,
           height:60,
           fontSize: 15,
           fontWeight: 'bold',
           flexDirection:'row',
          justifyContent:'center',
      },
       button:{
                 position:'relative',
                 flex:1,
                 backgroundColor:'#48BBEC',
                 bottom:170,
                 left:100,
                 height:35,
                 flexDirection:'row',
                 justifyContent:'center',
                 borderRadius:10,
            },
          buttontext:{
            flex:1,
            fontSize:15,
            color:'white',
            alignSelf:'center',
            },
      main:{
          flex:1,
          position:'absolute',
      },
 });

 AppRegistry.registerComponent('TestUI', () => TestQRAndroidExample);
