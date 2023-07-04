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
        DataZ        8                         
        Label        $$main                    
        PushI        1                         
        JumpFalse    -if-statement-1-end       
        PushI        1                         
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushF        2.200000                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_15_               
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        105                       %% "if block"
        DataC        102                       
        DataC        32                        
        DataC        98                        
        DataC        108                       
        DataC        111                       
        DataC        99                        
        DataC        107                       
        DataC        0                         
        PushD        _string_15_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -if-statement-1-end       
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushI        9                         
        PushI        10                        
        Multiply                               
        PushI        2                         
        Subtract                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushI        97                        
        PushI        97                        
        Label        -compare-2-arg1           
        Label        -compare-2-arg2           
        Label        -compare-2-sub            
        Subtract                               
        JumpFalse    -compare-2-true           
        Jump         -compare-2-false          
        Label        -compare-2-true           
        PushI        1                         
        Jump         -compare-2-join           
        Label        -compare-2-false          
        PushI        0                         
        Jump         -compare-2-join           
        Label        -compare-2-join           
        JumpFalse    -if-statement-3-elseBlock 
        DLabel       _string_14_               
        DataI        3                         
        DataI        9                         
        DataI        2                         
        DataC        104                       %% "hi"
        DataC        105                       
        DataC        0                         
        PushD        _string_14_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        DLabel       _string_13_               
        DataI        3                         
        DataI        9                         
        DataI        11                        
        DataC        115                       %% "some string"
        DataC        111                       
        DataC        109                       
        DataC        101                       
        DataC        32                        
        DataC        115                       
        DataC        116                       
        DataC        114                       
        DataC        105                       
        DataC        110                       
        DataC        103                       
        DataC        0                         
        PushD        _string_13_               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Jump         -if-statement-3-end       
        Label        -if-statement-3-elseBlock 
        DLabel       _string_12_               
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        101                       %% "else1..."
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        49                        
        DataC        46                        
        DataC        46                        
        DataC        46                        
        DataC        0                         
        PushD        _string_12_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -if-statement-3-end       
        PushI        97                        
        PushI        98                        
        Label        -compare-4-arg1           
        Label        -compare-4-arg2           
        Label        -compare-4-sub            
        Subtract                               
        JumpFalse    -compare-4-true           
        Jump         -compare-4-false          
        Label        -compare-4-true           
        PushI        1                         
        Jump         -compare-4-join           
        Label        -compare-4-false          
        PushI        0                         
        Jump         -compare-4-join           
        Label        -compare-4-join           
        JumpFalse    -if-statement-5-elseBlock 
        DLabel       _string_11_               
        DataI        3                         
        DataI        9                         
        DataI        2                         
        DataC        104                       %% "hi"
        DataC        105                       
        DataC        0                         
        PushD        _string_11_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        DLabel       _string_10_               
        DataI        3                         
        DataI        9                         
        DataI        11                        
        DataC        115                       %% "some string"
        DataC        111                       
        DataC        109                       
        DataC        101                       
        DataC        32                        
        DataC        115                       
        DataC        116                       
        DataC        114                       
        DataC        105                       
        DataC        110                       
        DataC        103                       
        DataC        0                         
        PushD        _string_10_               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Jump         -if-statement-5-end       
        Label        -if-statement-5-elseBlock 
        DLabel       _string_9_                
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        101                       %% "else2..."
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        50                        
        DataC        46                        
        DataC        46                        
        DataC        46                        
        DataC        0                         
        PushD        _string_9_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -if-statement-5-end       
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushI        3                         
        JumpTrue     -castIntToBool-6-true     
        PushI        0                         
        Jump         -castIntToBool-6-join     
        Label        -castIntToBool-6-true     
        PushI        1                         
        Label        -castIntToBool-6-join     
        JumpFalse    -if-statement-7-end       
        Label        -if-statement-7-end       
        DLabel       _string_8_                
        DataI        3                         
        DataI        9                         
        DataI        21                        
        DataC        101                       %% "empty if block test 1"
        DataC        109                       
        DataC        112                       
        DataC        116                       
        DataC        121                       
        DataC        32                        
        DataC        105                       
        DataC        102                       
        DataC        32                        
        DataC        98                        
        DataC        108                       
        DataC        111                       
        DataC        99                        
        DataC        107                       
        DataC        32                        
        DataC        116                       
        DataC        101                       
        DataC        115                       
        DataC        116                       
        DataC        32                        
        DataC        49                        
        DataC        0                         
        PushD        _string_8_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushI        1                         
        PushI        10                        
        Add                                    
        PushI        2                         
        Label        -compare-8-arg1           
        Label        -compare-8-arg2           
        Label        -compare-8-sub            
        Subtract                               
        JumpPos      -compare-8-true           
        Jump         -compare-8-false          
        Label        -compare-8-true           
        PushI        1                         
        Jump         -compare-8-join           
        Label        -compare-8-false          
        PushI        0                         
        Jump         -compare-8-join           
        Label        -compare-8-join           
        JumpFalse    -if-statement-9-elseBlock 
        Jump         -if-statement-9-end       
        Label        -if-statement-9-elseBlock 
        DLabel       _string_7_                
        DataI        3                         
        DataI        9                         
        DataI        7                         
        DataC        101                       %% "else..."
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        46                        
        DataC        46                        
        DataC        46                        
        DataC        0                         
        PushD        _string_7_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_6_                
        DataI        3                         
        DataI        9                         
        DataI        21                        
        DataC        101                       %% "empty if block test 2"
        DataC        109                       
        DataC        112                       
        DataC        116                       
        DataC        121                       
        DataC        32                        
        DataC        105                       
        DataC        102                       
        DataC        32                        
        DataC        98                        
        DataC        108                       
        DataC        111                       
        DataC        99                        
        DataC        107                       
        DataC        32                        
        DataC        116                       
        DataC        101                       
        DataC        115                       
        DataC        116                       
        DataC        32                        
        DataC        50                        
        DataC        0                         
        PushD        _string_6_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -if-statement-9-end       
        DLabel       _string_5_                
        DataI        3                         
        DataI        9                         
        DataI        21                        
        DataC        101                       %% "empty if block test 2"
        DataC        109                       
        DataC        112                       
        DataC        116                       
        DataC        121                       
        DataC        32                        
        DataC        105                       
        DataC        102                       
        DataC        32                        
        DataC        98                        
        DataC        108                       
        DataC        111                       
        DataC        99                        
        DataC        107                       
        DataC        32                        
        DataC        116                       
        DataC        101                       
        DataC        115                       
        DataC        116                       
        DataC        32                        
        DataC        50                        
        DataC        0                         
        PushD        _string_5_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushI        1                         
        PushI        10                        
        Add                                    
        PushI        2                         
        Label        -compare-10-arg1          
        Label        -compare-10-arg2          
        Label        -compare-10-sub           
        Subtract                               
        JumpPos      -compare-10-true          
        Jump         -compare-10-false         
        Label        -compare-10-true          
        PushI        1                         
        Jump         -compare-10-join          
        Label        -compare-10-false         
        PushI        0                         
        Jump         -compare-10-join          
        Label        -compare-10-join          
        JumpFalse    -if-statement-11-elseBlock 
        Jump         -if-statement-11-end      
        Label        -if-statement-11-elseBlock 
        DLabel       _string_4_                
        DataI        3                         
        DataI        9                         
        DataI        7                         
        DataC        101                       %% "else..."
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        46                        
        DataC        46                        
        DataC        46                        
        DataC        0                         
        PushD        _string_4_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_3_                
        DataI        3                         
        DataI        9                         
        DataI        21                        
        DataC        101                       %% "empty if block test 3"
        DataC        109                       
        DataC        112                       
        DataC        116                       
        DataC        121                       
        DataC        32                        
        DataC        105                       
        DataC        102                       
        DataC        32                        
        DataC        98                        
        DataC        108                       
        DataC        111                       
        DataC        99                        
        DataC        107                       
        DataC        32                        
        DataC        116                       
        DataC        101                       
        DataC        115                       
        DataC        116                       
        DataC        32                        
        DataC        51                        
        DataC        0                         
        PushD        _string_3_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -if-statement-11-end      
        DLabel       _string_2_                
        DataI        3                         
        DataI        9                         
        DataI        21                        
        DataC        101                       %% "empty if block test 3"
        DataC        109                       
        DataC        112                       
        DataC        116                       
        DataC        121                       
        DataC        32                        
        DataC        105                       
        DataC        102                       
        DataC        32                        
        DataC        98                        
        DataC        108                       
        DataC        111                       
        DataC        99                        
        DataC        107                       
        DataC        32                        
        DataC        116                       
        DataC        101                       
        DataC        115                       
        DataC        116                       
        DataC        32                        
        DataC        51                        
        DataC        0                         
        PushD        _string_2_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushI        0                         
        JumpFalse    -if-statement-12-elseBlock 
        PushI        10                        
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Jump         -if-statement-12-end      
        Label        -if-statement-12-elseBlock 
        Label        -if-statement-12-end      
        DLabel       _string_1_                
        DataI        3                         
        DataI        9                         
        DataI        21                        
        DataC        116                       %% "test empty else block"
        DataC        101                       
        DataC        115                       
        DataC        116                       
        DataC        32                        
        DataC        101                       
        DataC        109                       
        DataC        112                       
        DataC        116                       
        DataC        121                       
        DataC        32                        
        DataC        101                       
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        32                        
        DataC        98                        
        DataC        108                       
        DataC        111                       
        DataC        99                        
        DataC        107                       
        DataC        0                         
        PushD        _string_1_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
