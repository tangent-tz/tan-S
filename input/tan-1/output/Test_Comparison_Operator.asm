        Jump         $$main                    
        DLabel       $eat-location-zero        
        DataZ        8                         
        DLabel       $print-format-integer     
        DataC        37                        %% "%d"
        DataC        100                       
        DataC        0                         
        DLabel       $print-format-boolean     
        DataC        37                        %% "%s"
        DataC        115                       
        DataC        0                         
        DLabel       $print-format-float       
        DataC        37                        %% "%f"
        DataC        102                       
        DataC        0                         
        DLabel       $print-format-character   
        DataC        37                        %% "%c"
        DataC        99                        
        DataC        0                         
        DLabel       $print-format-string      
        DataC        37                        %% "%s"
        DataC        115                       
        DataC        0                         
        DLabel       $print-format-newline     
        DataC        10                        %% "\n"
        DataC        0                         
        DLabel       $print-format-space       
        DataC        32                        %% " "
        DataC        0                         
        DLabel       $print-format-tab         
        DataC        9                         %% "\t"
        DataC        0                         
        DLabel       $boolean-true-string      
        DataC        116                       %% "true"
        DataC        114                       
        DataC        117                       
        DataC        101                       
        DataC        0                         
        DLabel       $boolean-false-string     
        DataC        102                       %% "false"
        DataC        97                        
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        0                         
        DLabel       $errors-general-message   
        DataC        82                        %% "Runtime error: %s\n"
        DataC        117                       
        DataC        110                       
        DataC        116                       
        DataC        105                       
        DataC        109                       
        DataC        101                       
        DataC        32                        
        DataC        101                       
        DataC        114                       
        DataC        114                       
        DataC        111                       
        DataC        114                       
        DataC        58                        
        DataC        32                        
        DataC        37                        
        DataC        115                       
        DataC        10                        
        DataC        0                         
        Label        $$general-runtime-error   
        PushD        $errors-general-message   
        Printf                                 
        Halt                                   
        DLabel       $errors-int-divide-by-zero 
        DataC        105                       %% "integer divide by zero"
        DataC        110                       
        DataC        116                       
        DataC        101                       
        DataC        103                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        100                       
        DataC        105                       
        DataC        118                       
        DataC        105                       
        DataC        100                       
        DataC        101                       
        DataC        32                        
        DataC        98                        
        DataC        121                       
        DataC        32                        
        DataC        122                       
        DataC        101                       
        DataC        114                       
        DataC        111                       
        DataC        0                         
        Label        $$i-divide-by-zero        
        PushD        $errors-int-divide-by-zero 
        Jump         $$general-runtime-error   
        DLabel       $errors-float-divide-by-zero 
        DataC        102                       %% "float divide by zero"
        DataC        108                       
        DataC        111                       
        DataC        97                        
        DataC        116                       
        DataC        32                        
        DataC        100                       
        DataC        105                       
        DataC        118                       
        DataC        105                       
        DataC        100                       
        DataC        101                       
        DataC        32                        
        DataC        98                        
        DataC        121                       
        DataC        32                        
        DataC        122                       
        DataC        101                       
        DataC        114                       
        DataC        111                       
        DataC        0                         
        Label        $$f-divide-by-zero        
        PushD        $errors-float-divide-by-zero 
        Jump         $$general-runtime-error   
        DLabel       $usable-memory-start      
        DLabel       $global-memory-block      
        DataZ        384                       
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        PushI        5                         
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% b
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% resultlessthanint
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% b
        LoadI                                  
        Label        -compare-1-arg1           
        Label        -compare-1-arg2           
        Label        -compare-1-sub            
        Subtract                               
        JumpNeg      -compare-1-true           
        Jump         -compare-1-false          
        Label        -compare-1-true           
        PushI        1                         
        Jump         -compare-1-join           
        Label        -compare-1-false          
        PushI        0                         
        Jump         -compare-1-join           
        Label        -compare-1-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% resultlessthanint
        LoadC                                  
        JumpTrue     -print-boolean-2-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-2-join     
        Label        -print-boolean-2-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-2-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        9                         
        Add                                    %% c
        PushI        15                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        13                        
        Add                                    %% d
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        17                        
        Add                                    %% resultlessthannegativeint
        PushD        $global-memory-block      
        PushI        9                         
        Add                                    %% c
        LoadI                                  
        PushD        $global-memory-block      
        PushI        13                        
        Add                                    %% d
        LoadI                                  
        Label        -compare-3-arg1           
        Label        -compare-3-arg2           
        Label        -compare-3-sub            
        Subtract                               
        JumpNeg      -compare-3-true           
        Jump         -compare-3-false          
        Label        -compare-3-true           
        PushI        1                         
        Jump         -compare-3-join           
        Label        -compare-3-false          
        PushI        0                         
        Jump         -compare-3-join           
        Label        -compare-3-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        17                        
        Add                                    %% resultlessthannegativeint
        LoadC                                  
        JumpTrue     -print-boolean-4-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-4-join     
        Label        -print-boolean-4-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-4-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        18                        
        Add                                    %% e
        PushI        20                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        22                        
        Add                                    %% f
        PushI        20                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        26                        
        Add                                    %% resultlessthanorequalint
        PushD        $global-memory-block      
        PushI        18                        
        Add                                    %% e
        LoadI                                  
        PushD        $global-memory-block      
        PushI        22                        
        Add                                    %% f
        LoadI                                  
        Label        -compare-5-arg1           
        Label        -compare-5-arg2           
        Label        -compare-5-sub            
        Subtract                               
        JumpPos      -compare-5-false          
        Jump         -compare-5-true           
        Label        -compare-5-true           
        PushI        1                         
        Jump         -compare-5-join           
        Label        -compare-5-false          
        PushI        0                         
        Jump         -compare-5-join           
        Label        -compare-5-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        26                        
        Add                                    %% resultlessthanorequalint
        LoadC                                  
        JumpTrue     -print-boolean-6-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-6-join     
        Label        -print-boolean-6-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-6-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        27                        
        Add                                    %% g
        PushI        25                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        31                        
        Add                                    %% h
        PushI        30                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        35                        
        Add                                    %% resultlessthanorequalnegativeint
        PushD        $global-memory-block      
        PushI        27                        
        Add                                    %% g
        LoadI                                  
        PushD        $global-memory-block      
        PushI        31                        
        Add                                    %% h
        LoadI                                  
        Label        -compare-7-arg1           
        Label        -compare-7-arg2           
        Label        -compare-7-sub            
        Subtract                               
        JumpPos      -compare-7-false          
        Jump         -compare-7-true           
        Label        -compare-7-true           
        PushI        1                         
        Jump         -compare-7-join           
        Label        -compare-7-false          
        PushI        0                         
        Jump         -compare-7-join           
        Label        -compare-7-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        35                        
        Add                                    %% resultlessthanorequalnegativeint
        LoadC                                  
        JumpTrue     -print-boolean-8-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-8-join     
        Label        -print-boolean-8-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-8-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% i
        PushI        35                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% j
        PushI        35                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% resultequaltoint
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% i
        LoadI                                  
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% j
        LoadI                                  
        Label        -compare-9-arg1           
        Label        -compare-9-arg2           
        Label        -compare-9-sub            
        Subtract                               
        JumpFalse    -compare-9-true           
        Jump         -compare-9-false          
        Label        -compare-9-true           
        PushI        1                         
        Jump         -compare-9-join           
        Label        -compare-9-false          
        PushI        0                         
        Jump         -compare-9-join           
        Label        -compare-9-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% resultequaltoint
        LoadC                                  
        JumpTrue     -print-boolean-10-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-10-join    
        Label        -print-boolean-10-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-10-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        45                        
        Add                                    %% k
        PushI        40                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        49                        
        Add                                    %% l
        PushI        45                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        53                        
        Add                                    %% resultequaltonegativeint
        PushD        $global-memory-block      
        PushI        45                        
        Add                                    %% k
        LoadI                                  
        PushD        $global-memory-block      
        PushI        49                        
        Add                                    %% l
        LoadI                                  
        Label        -compare-11-arg1          
        Label        -compare-11-arg2          
        Label        -compare-11-sub           
        Subtract                               
        JumpFalse    -compare-11-true          
        Jump         -compare-11-false         
        Label        -compare-11-true          
        PushI        1                         
        Jump         -compare-11-join          
        Label        -compare-11-false         
        PushI        0                         
        Jump         -compare-11-join          
        Label        -compare-11-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        53                        
        Add                                    %% resultequaltonegativeint
        LoadC                                  
        JumpTrue     -print-boolean-12-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-12-join    
        Label        -print-boolean-12-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-12-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        54                        
        Add                                    %% m
        PushI        50                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        58                        
        Add                                    %% n
        PushI        60                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        62                        
        Add                                    %% resultnotequaltoint
        PushD        $global-memory-block      
        PushI        54                        
        Add                                    %% m
        LoadI                                  
        PushD        $global-memory-block      
        PushI        58                        
        Add                                    %% n
        LoadI                                  
        Label        -compare-13-arg1          
        Label        -compare-13-arg2          
        Label        -compare-13-sub           
        Subtract                               
        JumpFalse    -compare-13-false         
        Jump         -compare-13-true          
        Label        -compare-13-true          
        PushI        1                         
        Jump         -compare-13-join          
        Label        -compare-13-false         
        PushI        0                         
        Jump         -compare-13-join          
        Label        -compare-13-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        62                        
        Add                                    %% resultnotequaltoint
        LoadC                                  
        JumpTrue     -print-boolean-14-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-14-join    
        Label        -print-boolean-14-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-14-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        63                        
        Add                                    %% o
        PushI        65                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        67                        
        Add                                    %% p
        PushI        65                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        71                        
        Add                                    %% resultnotequaltonegativeint
        PushD        $global-memory-block      
        PushI        63                        
        Add                                    %% o
        LoadI                                  
        PushD        $global-memory-block      
        PushI        67                        
        Add                                    %% p
        LoadI                                  
        Label        -compare-15-arg1          
        Label        -compare-15-arg2          
        Label        -compare-15-sub           
        Subtract                               
        JumpFalse    -compare-15-false         
        Jump         -compare-15-true          
        Label        -compare-15-true          
        PushI        1                         
        Jump         -compare-15-join          
        Label        -compare-15-false         
        PushI        0                         
        Jump         -compare-15-join          
        Label        -compare-15-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        71                        
        Add                                    %% resultnotequaltonegativeint
        LoadC                                  
        JumpTrue     -print-boolean-16-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-16-join    
        Label        -print-boolean-16-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-16-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% q
        PushI        80                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        76                        
        Add                                    %% r
        PushI        75                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        80                        
        Add                                    %% resultgreaterthanint
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% q
        LoadI                                  
        PushD        $global-memory-block      
        PushI        76                        
        Add                                    %% r
        LoadI                                  
        Label        -compare-17-arg1          
        Label        -compare-17-arg2          
        Label        -compare-17-sub           
        Subtract                               
        JumpPos      -compare-17-true          
        Jump         -compare-17-false         
        Label        -compare-17-true          
        PushI        1                         
        Jump         -compare-17-join          
        Label        -compare-17-false         
        PushI        0                         
        Jump         -compare-17-join          
        Label        -compare-17-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        80                        
        Add                                    %% resultgreaterthanint
        LoadC                                  
        JumpTrue     -print-boolean-18-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-18-join    
        Label        -print-boolean-18-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-18-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        81                        
        Add                                    %% s
        PushI        80                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        85                        
        Add                                    %% t
        PushI        85                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        89                        
        Add                                    %% resultgreaterthannegativeint
        PushD        $global-memory-block      
        PushI        81                        
        Add                                    %% s
        LoadI                                  
        PushD        $global-memory-block      
        PushI        85                        
        Add                                    %% t
        LoadI                                  
        Label        -compare-19-arg1          
        Label        -compare-19-arg2          
        Label        -compare-19-sub           
        Subtract                               
        JumpPos      -compare-19-true          
        Jump         -compare-19-false         
        Label        -compare-19-true          
        PushI        1                         
        Jump         -compare-19-join          
        Label        -compare-19-false         
        PushI        0                         
        Jump         -compare-19-join          
        Label        -compare-19-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        89                        
        Add                                    %% resultgreaterthannegativeint
        LoadC                                  
        JumpTrue     -print-boolean-20-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-20-join    
        Label        -print-boolean-20-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-20-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        90                        
        Add                                    %% u
        PushI        90                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        94                        
        Add                                    %% v
        PushI        95                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        98                        
        Add                                    %% resultgreaterthanorequalint
        PushD        $global-memory-block      
        PushI        90                        
        Add                                    %% u
        LoadI                                  
        PushD        $global-memory-block      
        PushI        94                        
        Add                                    %% v
        LoadI                                  
        Label        -compare-21-arg1          
        Label        -compare-21-arg2          
        Label        -compare-21-sub           
        Subtract                               
        JumpNeg      -compare-21-false         
        Jump         -compare-21-true          
        Label        -compare-21-true          
        PushI        1                         
        Jump         -compare-21-join          
        Label        -compare-21-false         
        PushI        0                         
        Jump         -compare-21-join          
        Label        -compare-21-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        98                        
        Add                                    %% resultgreaterthanorequalint
        LoadC                                  
        JumpTrue     -print-boolean-22-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-22-join    
        Label        -print-boolean-22-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-22-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        99                        
        Add                                    %% w
        PushI        100                       
        StoreI                                 
        PushD        $global-memory-block      
        PushI        103                       
        Add                                    %% x
        PushI        105                       
        StoreI                                 
        PushD        $global-memory-block      
        PushI        107                       
        Add                                    %% resultgreaterthanorequalnegativeint
        PushD        $global-memory-block      
        PushI        99                        
        Add                                    %% w
        LoadI                                  
        PushD        $global-memory-block      
        PushI        103                       
        Add                                    %% x
        LoadI                                  
        Label        -compare-23-arg1          
        Label        -compare-23-arg2          
        Label        -compare-23-sub           
        Subtract                               
        JumpNeg      -compare-23-false         
        Jump         -compare-23-true          
        Label        -compare-23-true          
        PushI        1                         
        Jump         -compare-23-join          
        Label        -compare-23-false         
        PushI        0                         
        Jump         -compare-23-join          
        Label        -compare-23-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        107                       
        Add                                    %% resultgreaterthanorequalnegativeint
        LoadC                                  
        JumpTrue     -print-boolean-24-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-24-join    
        Label        -print-boolean-24-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-24-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        108                       
        Add                                    %% afloat
        PushF        5.000000                  
        FNegate      null                      
        StoreF                                 
        PushD        $global-memory-block      
        PushI        116                       
        Add                                    %% bfloat
        PushF        10.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        124                       
        Add                                    %% resultlessthanfloat
        PushD        $global-memory-block      
        PushI        108                       
        Add                                    %% afloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        116                       
        Add                                    %% bfloat
        LoadF                                  
        Label        -compare-25-arg1          
        Label        -compare-25-arg2          
        Label        -compare-25-sub           
        FSubtract    null                      
        JumpFNeg     -compare-25-true          
        Jump         -compare-25-false         
        Label        -compare-25-true          
        PushI        1                         
        Jump         -compare-25-join          
        Label        -compare-25-false         
        PushI        0                         
        Jump         -compare-25-join          
        Label        -compare-25-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        124                       
        Add                                    %% resultlessthanfloat
        LoadC                                  
        JumpTrue     -print-boolean-26-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-26-join    
        Label        -print-boolean-26-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-26-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        125                       
        Add                                    %% cfloat
        PushF        15.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        133                       
        Add                                    %% dfloat
        PushF        10.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        141                       
        Add                                    %% resultlessthannegativefloat
        PushD        $global-memory-block      
        PushI        125                       
        Add                                    %% cfloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        133                       
        Add                                    %% dfloat
        LoadF                                  
        Label        -compare-27-arg1          
        Label        -compare-27-arg2          
        Label        -compare-27-sub           
        FSubtract    null                      
        JumpFNeg     -compare-27-true          
        Jump         -compare-27-false         
        Label        -compare-27-true          
        PushI        1                         
        Jump         -compare-27-join          
        Label        -compare-27-false         
        PushI        0                         
        Jump         -compare-27-join          
        Label        -compare-27-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        141                       
        Add                                    %% resultlessthannegativefloat
        LoadC                                  
        JumpTrue     -print-boolean-28-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-28-join    
        Label        -print-boolean-28-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-28-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        142                       
        Add                                    %% efloat
        PushF        20.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        150                       
        Add                                    %% ffloat
        PushF        20.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        158                       
        Add                                    %% resultlessthanorequalfloat
        PushD        $global-memory-block      
        PushI        142                       
        Add                                    %% efloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        150                       
        Add                                    %% ffloat
        LoadF                                  
        Label        -compare-29-arg1          
        Label        -compare-29-arg2          
        Label        -compare-29-sub           
        FSubtract    null                      
        JumpFPos     -compare-29-false         
        Jump         -compare-29-true          
        Label        -compare-29-true          
        PushI        1                         
        Jump         -compare-29-join          
        Label        -compare-29-false         
        PushI        0                         
        Jump         -compare-29-join          
        Label        -compare-29-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        158                       
        Add                                    %% resultlessthanorequalfloat
        LoadC                                  
        JumpTrue     -print-boolean-30-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-30-join    
        Label        -print-boolean-30-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-30-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        159                       
        Add                                    %% gfloat
        PushF        25.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        167                       
        Add                                    %% hfloat
        PushF        30.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        175                       
        Add                                    %% resultlessthanorequalnegativefloat
        PushD        $global-memory-block      
        PushI        159                       
        Add                                    %% gfloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        167                       
        Add                                    %% hfloat
        LoadF                                  
        Label        -compare-31-arg1          
        Label        -compare-31-arg2          
        Label        -compare-31-sub           
        FSubtract    null                      
        JumpFPos     -compare-31-false         
        Jump         -compare-31-true          
        Label        -compare-31-true          
        PushI        1                         
        Jump         -compare-31-join          
        Label        -compare-31-false         
        PushI        0                         
        Jump         -compare-31-join          
        Label        -compare-31-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        175                       
        Add                                    %% resultlessthanorequalnegativefloat
        LoadC                                  
        JumpTrue     -print-boolean-32-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-32-join    
        Label        -print-boolean-32-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-32-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        176                       
        Add                                    %% ifloat
        PushF        35.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        184                       
        Add                                    %% jfloat
        PushF        35.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        192                       
        Add                                    %% resultequaltofloat
        PushD        $global-memory-block      
        PushI        176                       
        Add                                    %% ifloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        184                       
        Add                                    %% jfloat
        LoadF                                  
        Label        -compare-33-arg1          
        Label        -compare-33-arg2          
        Label        -compare-33-sub           
        FSubtract    null                      
        JumpFZero    -compare-33-true          
        Jump         -compare-33-false         
        Label        -compare-33-true          
        PushI        1                         
        Jump         -compare-33-join          
        Label        -compare-33-false         
        PushI        0                         
        Jump         -compare-33-join          
        Label        -compare-33-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        192                       
        Add                                    %% resultequaltofloat
        LoadC                                  
        JumpTrue     -print-boolean-34-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-34-join    
        Label        -print-boolean-34-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-34-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        193                       
        Add                                    %% kfloat
        PushF        40.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        201                       
        Add                                    %% lfloat
        PushF        45.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        209                       
        Add                                    %% resultequaltonegativefloat
        PushD        $global-memory-block      
        PushI        193                       
        Add                                    %% kfloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        201                       
        Add                                    %% lfloat
        LoadF                                  
        Label        -compare-35-arg1          
        Label        -compare-35-arg2          
        Label        -compare-35-sub           
        FSubtract    null                      
        JumpFZero    -compare-35-true          
        Jump         -compare-35-false         
        Label        -compare-35-true          
        PushI        1                         
        Jump         -compare-35-join          
        Label        -compare-35-false         
        PushI        0                         
        Jump         -compare-35-join          
        Label        -compare-35-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        209                       
        Add                                    %% resultequaltonegativefloat
        LoadC                                  
        JumpTrue     -print-boolean-36-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-36-join    
        Label        -print-boolean-36-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-36-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        210                       
        Add                                    %% mfloat
        PushF        50.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        218                       
        Add                                    %% nfloat
        PushF        60.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        226                       
        Add                                    %% resultnotequaltofloat
        PushD        $global-memory-block      
        PushI        210                       
        Add                                    %% mfloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        218                       
        Add                                    %% nfloat
        LoadF                                  
        Label        -compare-37-arg1          
        Label        -compare-37-arg2          
        Label        -compare-37-sub           
        FSubtract    null                      
        JumpFZero    -compare-37-false         
        Jump         -compare-37-true          
        Label        -compare-37-true          
        PushI        1                         
        Jump         -compare-37-join          
        Label        -compare-37-false         
        PushI        0                         
        Jump         -compare-37-join          
        Label        -compare-37-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        226                       
        Add                                    %% resultnotequaltofloat
        LoadC                                  
        JumpTrue     -print-boolean-38-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-38-join    
        Label        -print-boolean-38-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-38-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        227                       
        Add                                    %% ofloat
        PushF        65.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        235                       
        Add                                    %% pfloat
        PushF        65.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        243                       
        Add                                    %% resultnotequaltonegativefloat
        PushD        $global-memory-block      
        PushI        227                       
        Add                                    %% ofloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        235                       
        Add                                    %% pfloat
        LoadF                                  
        Label        -compare-39-arg1          
        Label        -compare-39-arg2          
        Label        -compare-39-sub           
        FSubtract    null                      
        JumpFZero    -compare-39-false         
        Jump         -compare-39-true          
        Label        -compare-39-true          
        PushI        1                         
        Jump         -compare-39-join          
        Label        -compare-39-false         
        PushI        0                         
        Jump         -compare-39-join          
        Label        -compare-39-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        243                       
        Add                                    %% resultnotequaltonegativefloat
        LoadC                                  
        JumpTrue     -print-boolean-40-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-40-join    
        Label        -print-boolean-40-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-40-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        244                       
        Add                                    %% qfloat
        PushF        80.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        252                       
        Add                                    %% rfloat
        PushF        75.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        260                       
        Add                                    %% resultgreaterthanfloat
        PushD        $global-memory-block      
        PushI        244                       
        Add                                    %% qfloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        252                       
        Add                                    %% rfloat
        LoadF                                  
        Label        -compare-41-arg1          
        Label        -compare-41-arg2          
        Label        -compare-41-sub           
        FSubtract    null                      
        JumpFPos     -compare-41-true          
        Jump         -compare-41-false         
        Label        -compare-41-true          
        PushI        1                         
        Jump         -compare-41-join          
        Label        -compare-41-false         
        PushI        0                         
        Jump         -compare-41-join          
        Label        -compare-41-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        260                       
        Add                                    %% resultgreaterthanfloat
        LoadC                                  
        JumpTrue     -print-boolean-42-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-42-join    
        Label        -print-boolean-42-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-42-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        261                       
        Add                                    %% sfloat
        PushF        80.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        269                       
        Add                                    %% tfloat
        PushF        85.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        277                       
        Add                                    %% resultgreaterthannegativefloat
        PushD        $global-memory-block      
        PushI        261                       
        Add                                    %% sfloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        269                       
        Add                                    %% tfloat
        LoadF                                  
        Label        -compare-43-arg1          
        Label        -compare-43-arg2          
        Label        -compare-43-sub           
        FSubtract    null                      
        JumpFPos     -compare-43-true          
        Jump         -compare-43-false         
        Label        -compare-43-true          
        PushI        1                         
        Jump         -compare-43-join          
        Label        -compare-43-false         
        PushI        0                         
        Jump         -compare-43-join          
        Label        -compare-43-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        277                       
        Add                                    %% resultgreaterthannegativefloat
        LoadC                                  
        JumpTrue     -print-boolean-44-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-44-join    
        Label        -print-boolean-44-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-44-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        278                       
        Add                                    %% ufloat
        PushF        90.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        286                       
        Add                                    %% vfloat
        PushF        95.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        294                       
        Add                                    %% resultgreaterthanorequalfloat
        PushD        $global-memory-block      
        PushI        278                       
        Add                                    %% ufloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        286                       
        Add                                    %% vfloat
        LoadF                                  
        Label        -compare-45-arg1          
        Label        -compare-45-arg2          
        Label        -compare-45-sub           
        FSubtract    null                      
        JumpFNeg     -compare-45-false         
        Jump         -compare-45-true          
        Label        -compare-45-true          
        PushI        1                         
        Jump         -compare-45-join          
        Label        -compare-45-false         
        PushI        0                         
        Jump         -compare-45-join          
        Label        -compare-45-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        294                       
        Add                                    %% resultgreaterthanorequalfloat
        LoadC                                  
        JumpTrue     -print-boolean-46-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-46-join    
        Label        -print-boolean-46-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-46-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        295                       
        Add                                    %% wfloat
        PushF        100.000000                
        StoreF                                 
        PushD        $global-memory-block      
        PushI        303                       
        Add                                    %% xfloat
        PushF        105.000000                
        StoreF                                 
        PushD        $global-memory-block      
        PushI        311                       
        Add                                    %% resultgreaterthanorequalnegativefloat
        PushD        $global-memory-block      
        PushI        295                       
        Add                                    %% wfloat
        LoadF                                  
        PushD        $global-memory-block      
        PushI        303                       
        Add                                    %% xfloat
        LoadF                                  
        Label        -compare-47-arg1          
        Label        -compare-47-arg2          
        Label        -compare-47-sub           
        FSubtract    null                      
        JumpFNeg     -compare-47-false         
        Jump         -compare-47-true          
        Label        -compare-47-true          
        PushI        1                         
        Jump         -compare-47-join          
        Label        -compare-47-false         
        PushI        0                         
        Jump         -compare-47-join          
        Label        -compare-47-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        311                       
        Add                                    %% resultgreaterthanorequalnegativefloat
        LoadC                                  
        JumpTrue     -print-boolean-48-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-48-join    
        Label        -print-boolean-48-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-48-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        312                       
        Add                                    %% achar
        PushI        97                        
        StoreC                                 
        PushD        $global-memory-block      
        PushI        313                       
        Add                                    %% bchar
        PushI        122                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        314                       
        Add                                    %% resultlessthanchar
        PushD        $global-memory-block      
        PushI        312                       
        Add                                    %% achar
        LoadC                                  
        PushD        $global-memory-block      
        PushI        313                       
        Add                                    %% bchar
        LoadC                                  
        Label        -compare-49-arg1          
        Label        -compare-49-arg2          
        Label        -compare-49-sub           
        Subtract                               
        JumpNeg      -compare-49-true          
        Jump         -compare-49-false         
        Label        -compare-49-true          
        PushI        1                         
        Jump         -compare-49-join          
        Label        -compare-49-false         
        PushI        0                         
        Jump         -compare-49-join          
        Label        -compare-49-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        314                       
        Add                                    %% resultlessthanchar
        LoadC                                  
        JumpTrue     -print-boolean-50-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-50-join    
        Label        -print-boolean-50-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-50-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        315                       
        Add                                    %% cchar
        PushI        67                        
        StoreC                                 
        PushD        $global-memory-block      
        PushI        316                       
        Add                                    %% dchar
        PushI        66                        
        StoreC                                 
        PushD        $global-memory-block      
        PushI        317                       
        Add                                    %% resultlessthannegativechar
        PushD        $global-memory-block      
        PushI        315                       
        Add                                    %% cchar
        LoadC                                  
        PushD        $global-memory-block      
        PushI        316                       
        Add                                    %% dchar
        LoadC                                  
        Label        -compare-51-arg1          
        Label        -compare-51-arg2          
        Label        -compare-51-sub           
        Subtract                               
        JumpNeg      -compare-51-true          
        Jump         -compare-51-false         
        Label        -compare-51-true          
        PushI        1                         
        Jump         -compare-51-join          
        Label        -compare-51-false         
        PushI        0                         
        Jump         -compare-51-join          
        Label        -compare-51-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        317                       
        Add                                    %% resultlessthannegativechar
        LoadC                                  
        JumpTrue     -print-boolean-52-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-52-join    
        Label        -print-boolean-52-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-52-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        318                       
        Add                                    %% echar
        PushI        99                        
        StoreC                                 
        PushD        $global-memory-block      
        PushI        319                       
        Add                                    %% fchar
        PushI        99                        
        StoreC                                 
        PushD        $global-memory-block      
        PushI        320                       
        Add                                    %% resultlessthanorequalchar
        PushD        $global-memory-block      
        PushI        318                       
        Add                                    %% echar
        LoadC                                  
        PushD        $global-memory-block      
        PushI        319                       
        Add                                    %% fchar
        LoadC                                  
        Label        -compare-53-arg1          
        Label        -compare-53-arg2          
        Label        -compare-53-sub           
        Subtract                               
        JumpPos      -compare-53-false         
        Jump         -compare-53-true          
        Label        -compare-53-true          
        PushI        1                         
        Jump         -compare-53-join          
        Label        -compare-53-false         
        PushI        0                         
        Jump         -compare-53-join          
        Label        -compare-53-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        320                       
        Add                                    %% resultlessthanorequalchar
        LoadC                                  
        JumpTrue     -print-boolean-54-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-54-join    
        Label        -print-boolean-54-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-54-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        321                       
        Add                                    %% gchar
        PushI        100                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        322                       
        Add                                    %% hchar
        PushI        99                        
        StoreC                                 
        PushD        $global-memory-block      
        PushI        323                       
        Add                                    %% resultlessthanorequalnegativechar
        PushD        $global-memory-block      
        PushI        321                       
        Add                                    %% gchar
        LoadC                                  
        PushD        $global-memory-block      
        PushI        322                       
        Add                                    %% hchar
        LoadC                                  
        Label        -compare-55-arg1          
        Label        -compare-55-arg2          
        Label        -compare-55-sub           
        Subtract                               
        JumpPos      -compare-55-false         
        Jump         -compare-55-true          
        Label        -compare-55-true          
        PushI        1                         
        Jump         -compare-55-join          
        Label        -compare-55-false         
        PushI        0                         
        Jump         -compare-55-join          
        Label        -compare-55-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        323                       
        Add                                    %% resultlessthanorequalnegativechar
        LoadC                                  
        JumpTrue     -print-boolean-56-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-56-join    
        Label        -print-boolean-56-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-56-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        324                       
        Add                                    %% ichar
        PushI        101                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        325                       
        Add                                    %% jchar
        PushI        101                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        326                       
        Add                                    %% resultequaltochar
        PushD        $global-memory-block      
        PushI        324                       
        Add                                    %% ichar
        LoadC                                  
        PushD        $global-memory-block      
        PushI        325                       
        Add                                    %% jchar
        LoadC                                  
        Label        -compare-57-arg1          
        Label        -compare-57-arg2          
        Label        -compare-57-sub           
        Subtract                               
        JumpFalse    -compare-57-true          
        Jump         -compare-57-false         
        Label        -compare-57-true          
        PushI        1                         
        Jump         -compare-57-join          
        Label        -compare-57-false         
        PushI        0                         
        Jump         -compare-57-join          
        Label        -compare-57-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        326                       
        Add                                    %% resultequaltochar
        LoadC                                  
        JumpTrue     -print-boolean-58-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-58-join    
        Label        -print-boolean-58-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-58-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        327                       
        Add                                    %% kchar
        PushI        102                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        328                       
        Add                                    %% lchar
        PushI        103                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        329                       
        Add                                    %% resultequaltonegativechar
        PushD        $global-memory-block      
        PushI        327                       
        Add                                    %% kchar
        LoadC                                  
        PushD        $global-memory-block      
        PushI        328                       
        Add                                    %% lchar
        LoadC                                  
        Label        -compare-59-arg1          
        Label        -compare-59-arg2          
        Label        -compare-59-sub           
        Subtract                               
        JumpFalse    -compare-59-true          
        Jump         -compare-59-false         
        Label        -compare-59-true          
        PushI        1                         
        Jump         -compare-59-join          
        Label        -compare-59-false         
        PushI        0                         
        Jump         -compare-59-join          
        Label        -compare-59-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        329                       
        Add                                    %% resultequaltonegativechar
        LoadC                                  
        JumpTrue     -print-boolean-60-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-60-join    
        Label        -print-boolean-60-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-60-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        330                       
        Add                                    %% mchar
        PushI        104                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        331                       
        Add                                    %% nchar
        PushI        105                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        332                       
        Add                                    %% resultnotequaltochar
        PushD        $global-memory-block      
        PushI        330                       
        Add                                    %% mchar
        LoadC                                  
        PushD        $global-memory-block      
        PushI        331                       
        Add                                    %% nchar
        LoadC                                  
        Label        -compare-61-arg1          
        Label        -compare-61-arg2          
        Label        -compare-61-sub           
        Subtract                               
        JumpFalse    -compare-61-false         
        Jump         -compare-61-true          
        Label        -compare-61-true          
        PushI        1                         
        Jump         -compare-61-join          
        Label        -compare-61-false         
        PushI        0                         
        Jump         -compare-61-join          
        Label        -compare-61-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        332                       
        Add                                    %% resultnotequaltochar
        LoadC                                  
        JumpTrue     -print-boolean-62-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-62-join    
        Label        -print-boolean-62-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-62-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        333                       
        Add                                    %% ochar
        PushI        106                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        334                       
        Add                                    %% pchar
        PushI        106                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        335                       
        Add                                    %% resultnotequaltonegativechar
        PushD        $global-memory-block      
        PushI        333                       
        Add                                    %% ochar
        LoadC                                  
        PushD        $global-memory-block      
        PushI        334                       
        Add                                    %% pchar
        LoadC                                  
        Label        -compare-63-arg1          
        Label        -compare-63-arg2          
        Label        -compare-63-sub           
        Subtract                               
        JumpFalse    -compare-63-false         
        Jump         -compare-63-true          
        Label        -compare-63-true          
        PushI        1                         
        Jump         -compare-63-join          
        Label        -compare-63-false         
        PushI        0                         
        Jump         -compare-63-join          
        Label        -compare-63-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        335                       
        Add                                    %% resultnotequaltonegativechar
        LoadC                                  
        JumpTrue     -print-boolean-64-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-64-join    
        Label        -print-boolean-64-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-64-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        336                       
        Add                                    %% ibool
        PushI        0                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        337                       
        Add                                    %% jbool
        PushI        0                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        338                       
        Add                                    %% resultequaltobool
        PushD        $global-memory-block      
        PushI        336                       
        Add                                    %% ibool
        LoadC                                  
        PushD        $global-memory-block      
        PushI        337                       
        Add                                    %% jbool
        LoadC                                  
        Label        -compare-65-arg1          
        Label        -compare-65-arg2          
        Label        -compare-65-sub           
        Subtract                               
        JumpFalse    -compare-65-true          
        Jump         -compare-65-false         
        Label        -compare-65-true          
        PushI        1                         
        Jump         -compare-65-join          
        Label        -compare-65-false         
        PushI        0                         
        Jump         -compare-65-join          
        Label        -compare-65-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        338                       
        Add                                    %% resultequaltobool
        LoadC                                  
        JumpTrue     -print-boolean-66-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-66-join    
        Label        -print-boolean-66-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-66-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        339                       
        Add                                    %% kbool
        PushI        1                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        340                       
        Add                                    %% lbool
        PushI        0                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        341                       
        Add                                    %% resultequaltonegativebool
        PushD        $global-memory-block      
        PushI        339                       
        Add                                    %% kbool
        LoadC                                  
        PushD        $global-memory-block      
        PushI        340                       
        Add                                    %% lbool
        LoadC                                  
        Label        -compare-67-arg1          
        Label        -compare-67-arg2          
        Label        -compare-67-sub           
        Subtract                               
        JumpFalse    -compare-67-true          
        Jump         -compare-67-false         
        Label        -compare-67-true          
        PushI        1                         
        Jump         -compare-67-join          
        Label        -compare-67-false         
        PushI        0                         
        Jump         -compare-67-join          
        Label        -compare-67-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        341                       
        Add                                    %% resultequaltonegativebool
        LoadC                                  
        JumpTrue     -print-boolean-68-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-68-join    
        Label        -print-boolean-68-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-68-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        342                       
        Add                                    %% mbool
        PushI        1                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        343                       
        Add                                    %% nbool
        PushI        0                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        344                       
        Add                                    %% resultnotequaltobool
        PushD        $global-memory-block      
        PushI        342                       
        Add                                    %% mbool
        LoadC                                  
        PushD        $global-memory-block      
        PushI        343                       
        Add                                    %% nbool
        LoadC                                  
        Label        -compare-69-arg1          
        Label        -compare-69-arg2          
        Label        -compare-69-sub           
        Subtract                               
        JumpFalse    -compare-69-false         
        Jump         -compare-69-true          
        Label        -compare-69-true          
        PushI        1                         
        Jump         -compare-69-join          
        Label        -compare-69-false         
        PushI        0                         
        Jump         -compare-69-join          
        Label        -compare-69-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        344                       
        Add                                    %% resultnotequaltobool
        LoadC                                  
        JumpTrue     -print-boolean-70-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-70-join    
        Label        -print-boolean-70-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-70-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        345                       
        Add                                    %% obool
        PushI        0                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        346                       
        Add                                    %% pbool
        PushI        0                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        347                       
        Add                                    %% resultnotequaltonegativebool
        PushD        $global-memory-block      
        PushI        345                       
        Add                                    %% obool
        LoadC                                  
        PushD        $global-memory-block      
        PushI        346                       
        Add                                    %% pbool
        LoadC                                  
        Label        -compare-71-arg1          
        Label        -compare-71-arg2          
        Label        -compare-71-sub           
        Subtract                               
        JumpFalse    -compare-71-false         
        Jump         -compare-71-true          
        Label        -compare-71-true          
        PushI        1                         
        Jump         -compare-71-join          
        Label        -compare-71-false         
        PushI        0                         
        Jump         -compare-71-join          
        Label        -compare-71-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        347                       
        Add                                    %% resultnotequaltonegativebool
        LoadC                                  
        JumpTrue     -print-boolean-72-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-72-join    
        Label        -print-boolean-72-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-72-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        348                       
        Add                                    %% istr
        DLabel       _string_6_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        104                       %% "hello"
        DataC        101                       
        DataC        108                       
        DataC        108                       
        DataC        111                       
        DataC        0                         
        PushD        _string_6_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        352                       
        Add                                    %% jstr
        DLabel       _string_5_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        104                       %% "hello"
        DataC        101                       
        DataC        108                       
        DataC        108                       
        DataC        111                       
        DataC        0                         
        PushD        _string_5_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        356                       
        Add                                    %% resultequaltostr
        PushD        $global-memory-block      
        PushI        348                       
        Add                                    %% istr
        LoadI                                  
        PushD        $global-memory-block      
        PushI        352                       
        Add                                    %% jstr
        LoadI                                  
        Label        -compare-73-arg1          
        Label        -compare-73-arg2          
        Label        -compare-73-sub           
        Subtract                               
        JumpFalse    -compare-73-true          
        Jump         -compare-73-false         
        Label        -compare-73-true          
        PushI        1                         
        Jump         -compare-73-join          
        Label        -compare-73-false         
        PushI        0                         
        Jump         -compare-73-join          
        Label        -compare-73-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        356                       
        Add                                    %% resultequaltostr
        LoadC                                  
        JumpTrue     -print-boolean-74-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-74-join    
        Label        -print-boolean-74-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-74-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        357                       
        Add                                    %% kstr
        DLabel       _string_4_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        104                       %% "hello"
        DataC        101                       
        DataC        108                       
        DataC        108                       
        DataC        111                       
        DataC        0                         
        PushD        _string_4_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        361                       
        Add                                    %% lstr
        PushD        $global-memory-block      
        PushI        357                       
        Add                                    %% kstr
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        365                       
        Add                                    %% resultequaltonegativestr
        PushD        $global-memory-block      
        PushI        357                       
        Add                                    %% kstr
        LoadI                                  
        PushD        $global-memory-block      
        PushI        361                       
        Add                                    %% lstr
        LoadI                                  
        Label        -compare-75-arg1          
        Label        -compare-75-arg2          
        Label        -compare-75-sub           
        Subtract                               
        JumpFalse    -compare-75-true          
        Jump         -compare-75-false         
        Label        -compare-75-true          
        PushI        1                         
        Jump         -compare-75-join          
        Label        -compare-75-false         
        PushI        0                         
        Jump         -compare-75-join          
        Label        -compare-75-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        365                       
        Add                                    %% resultequaltonegativestr
        LoadC                                  
        JumpTrue     -print-boolean-76-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-76-join    
        Label        -print-boolean-76-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-76-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        366                       
        Add                                    %% mstr
        DLabel       _string_3_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        104                       %% "hello"
        DataC        101                       
        DataC        108                       
        DataC        108                       
        DataC        111                       
        DataC        0                         
        PushD        _string_3_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        370                       
        Add                                    %% nstr
        DLabel       _string_2_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        104                       %% "hello"
        DataC        101                       
        DataC        108                       
        DataC        108                       
        DataC        111                       
        DataC        0                         
        PushD        _string_2_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        374                       
        Add                                    %% resultnotequaltostr
        PushD        $global-memory-block      
        PushI        366                       
        Add                                    %% mstr
        LoadI                                  
        PushD        $global-memory-block      
        PushI        370                       
        Add                                    %% nstr
        LoadI                                  
        Label        -compare-77-arg1          
        Label        -compare-77-arg2          
        Label        -compare-77-sub           
        Subtract                               
        JumpFalse    -compare-77-false         
        Jump         -compare-77-true          
        Label        -compare-77-true          
        PushI        1                         
        Jump         -compare-77-join          
        Label        -compare-77-false         
        PushI        0                         
        Jump         -compare-77-join          
        Label        -compare-77-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        374                       
        Add                                    %% resultnotequaltostr
        LoadC                                  
        JumpTrue     -print-boolean-78-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-78-join    
        Label        -print-boolean-78-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-78-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        375                       
        Add                                    %% ostr
        DLabel       _string_1_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        104                       %% "hello"
        DataC        101                       
        DataC        108                       
        DataC        108                       
        DataC        111                       
        DataC        0                         
        PushD        _string_1_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        379                       
        Add                                    %% pstr
        PushD        $global-memory-block      
        PushI        375                       
        Add                                    %% ostr
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        383                       
        Add                                    %% resultnotequaltonegativestr
        PushD        $global-memory-block      
        PushI        375                       
        Add                                    %% ostr
        LoadI                                  
        PushD        $global-memory-block      
        PushI        379                       
        Add                                    %% pstr
        LoadI                                  
        Label        -compare-79-arg1          
        Label        -compare-79-arg2          
        Label        -compare-79-sub           
        Subtract                               
        JumpFalse    -compare-79-false         
        Jump         -compare-79-true          
        Label        -compare-79-true          
        PushI        1                         
        Jump         -compare-79-join          
        Label        -compare-79-false         
        PushI        0                         
        Jump         -compare-79-join          
        Label        -compare-79-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        383                       
        Add                                    %% resultnotequaltonegativestr
        LoadC                                  
        JumpTrue     -print-boolean-80-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-80-join    
        Label        -print-boolean-80-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-80-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
