# TohokuTechDojo TimerSample
このアプリは東北Tech道場 石巻道場講義用に作成をしたタイマーサンプルアプリです。  
  
![Capture](https://raw.githubusercontent.com/TomiGie/CupmenTimer/master/captures/MainActivity.png "Capture")


## Getting Started
こちらのサンプルでは、CowntDowuTimerクラスを利用して指定の秒数から0になるまでをカウントダウンする機能が実装されています。  
また、カウントダウンの途中で、ある秒数が経過すると画像を変更するメソッドも用意してあります。    
  
※但し、初期状態ではカウントダウン中の画像の変更は設定されておりませんので、各自で変更する必要があります。
  
以下に変更が必要な部分とその手順を記載します。

### 画像の変更方法
このプログラムでは、カウントダウンがスタートされると1秒毎に以下のcheckCupmenState(int checkTime)メソッドが呼びだされます。  
```java:MainActivity.java
/**
 * カップ麺の状態をチェックして画像を変更する
 * CupmenTimerの onTick() で1秒毎に呼ばれる
 */
public void checkCupmenState(int checkTime) {
    // ラーメンが伸びる時間を過ぎたら
    if (checkTime <= CUPMEN_BAD_TIME) {
        cupmenImage.setImageResource(R.mipmap.men_waiting); // ①
    }
    // ラーメン完成の時間が過ぎたら
    else if (checkTime <= CUPMEN_COMPLETE_TIME) {
        cupmenImage.setImageResource(R.mipmap.men_waiting); // ②
    }
    // その他(ラーメン完成前)
    else {
        cupmenImage.setImageResource(R.mipmap.men_waiting); // ③
    }
}
```
このメソッドでは、呼び出された時の時間がどの(画像の)状態にあるかをチェックし、その時間に対応した画像をセットしています。
画像を変更したい場合は①②③の部分をそれぞれに対応した画像に指定すれば、変更が可能です。  
デフォルトで設定している画像の他にも2種類、mipmapフォルダに画像を用意してありますので、そちらを利用して頂いて大丈夫です。  
  
#License

Copyright 2014 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
