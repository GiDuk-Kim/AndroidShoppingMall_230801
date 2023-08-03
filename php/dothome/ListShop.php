<?php
    $con = mysqli_connect("localhost", "gdkgate", "kgd951753#", "gdkgate") or die("MySqlDB 접속 실패 !!");	// dothome.co.kr
  // $con = mysqli_connect("db-instance-001.cfaktkh1zamw.ap-northeast-2.rds.amazonaws.com", "admin", "admin001", "scott") or die("MySqlDB 접속 실패 !!");
    mysqli_query($con,'SET NAMES utf8');

    $category = isset($_POST["category"]) ? $_POST["category"] : "";   
    //$category = isset($_GET["category"]) ? $_GET["category"] : "";
  
    switch( $category )
    {
      case "all":
        $sql  = "select t.* from( select g.goods_id,g.goods_title,g.goods_price,d.fileName,g.goods_sort from t_shopping_goods g, t_goods_detail_image d where g.goods_id=d.goods_id and g.goods_del_yn='N' and d.filetype='main_image' order by g.goods_credate desc ) t;";    
        break;
      case "화환":
        $sql  = "select t.* from( select g.goods_id,g.goods_title,g.goods_price,d.fileName,g.goods_sort from t_shopping_goods g, t_goods_detail_image d where g.goods_id=d.goods_id and g.goods_del_yn='N' and d.filetype='main_image' and g.goods_sort in ('근조화환','축하화환') order by g.goods_credate desc ) t;";    
        break;
      case "관상":  
      case "관상식물":
        $sql  = "select t.* from( select g.goods_id,g.goods_title,g.goods_price,d.fileName,g.goods_sort from t_shopping_goods g, t_goods_detail_image d where g.goods_id=d.goods_id and g.goods_del_yn='N' and d.filetype='main_image' and g.goods_sort in ('다육이','동양란','서양란') order by g.goods_credate desc ) t;";    
        break;  
      case "기능성":  
      case "기능성식물":
        $sql  = "select t.* from( select g.goods_id,g.goods_title,g.goods_price,d.fileName,g.goods_sort from t_shopping_goods g, t_goods_detail_image d where g.goods_id=d.goods_id and g.goods_del_yn='N' and d.filetype='main_image' and g.goods_sort in ('공기정화식물','관엽') order by g.goods_credate desc ) t;";    
        break;
      case "꽃배달":  
      case "꽃배달서비스":
        $sql  = "select t.* from( select g.goods_id,g.goods_title,g.goods_price,d.fileName,g.goods_sort from t_shopping_goods g, t_goods_detail_image d where g.goods_id=d.goods_id and g.goods_del_yn='N' and d.filetype='main_image' and g.goods_sort in ('꽃다발') order by g.goods_credate desc ) t;";    
        break;  
      case "기타":
      case "부가제품":
        $sql  = "select t.* from( select g.goods_id,g.goods_title,g.goods_price,d.fileName,g.goods_sort from t_shopping_goods g, t_goods_detail_image d where g.goods_id=d.goods_id and g.goods_del_yn='N' and d.filetype='main_image' and g.goods_sort in ('분재','꽃박스') order by g.goods_credate desc ) t;";    
        break;        
    }
    
   
    $statement = mysqli_prepare($con, $sql);
   // mysqli_stmt_bind_param($statement, "s", $category);  // ss 는 string string,  si는 string int 의미임  
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $goods_id, $goods_title, $goods_price, $fileName, $goods_sort);

    $result = array();
    $response = array();
    if ( $statement == "") $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $result["success"] = true;
        $result["goods_id"] = $goods_id;
        $result["goods_title"] = $goods_title;
        $result["goods_price"] = $goods_price;   
        $result["fileName"] = $fileName;   
        $result["goods_sort"] = $goods_sort;
        $response[] = $result;          
    }

    echo json_encode($response);
?>