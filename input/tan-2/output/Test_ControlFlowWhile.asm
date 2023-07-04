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
        DataZ        20                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushI        0                         
        StoreI                                 
        Label        -while-statement-2--while-start 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        5                         
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
        JumpFalse    -while-statement-2--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-statement-2--while-start 
        Label        -while-statement-2--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushI        10                        
        StoreI                                 
        Label        -while-statement-4--while-start 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        5                         
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
        JumpFalse    -while-statement-4--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-statement-4--while-start 
        Label        -while-statement-4--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushI        1                         
        StoreI                                 
        Label        -while-statement-6--while-start 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        100                       
        Label        -compare-5-arg1           
        Label        -compare-5-arg2           
        Label        -compare-5-sub            
        Subtract                               
        JumpNeg      -compare-5-true           
        Jump         -compare-5-false          
        Label        -compare-5-true           
        PushI        1                         
        Jump         -compare-5-join           
        Label        -compare-5-false          
        PushI        0                         
        Jump         -compare-5-join           
        Label        -compare-5-join           
        JumpFalse    -while-statement-6--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        2                         
        Multiply                               
        StoreI                                 
        Jump         -while-statement-6--while-start 
        Label        -while-statement-6--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushI        0                         
        StoreI                                 
        Label        -while-statement-10--while-start 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        3                         
        Label        -compare-7-arg1           
        Label        -compare-7-arg2           
        Label        -compare-7-sub            
        Subtract                               
        JumpNeg      -compare-7-true           
        Jump         -compare-7-false          
        Label        -compare-7-true           
        PushI        1                         
        Jump         -compare-7-join           
        Label        -compare-7-false          
        PushI        0                         
        Jump         -compare-7-join           
        Label        -compare-7-join           
        JumpFalse    -while-statement-10--while-end 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        PushI        0                         
        StoreI                                 
        Label        -while-statement-9--while-start 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        PushI        3                         
        Label        -compare-8-arg1           
        Label        -compare-8-arg2           
        Label        -compare-8-sub            
        Subtract                               
        JumpNeg      -compare-8-true           
        Jump         -compare-8-false          
        Label        -compare-8-true           
        PushI        1                         
        Jump         -compare-8-join           
        Label        -compare-8-false          
        PushI        0                         
        Jump         -compare-8-join           
        Label        -compare-8-join           
        JumpFalse    -while-statement-9--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-statement-9--while-start 
        Label        -while-statement-9--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-statement-10--while-start 
        Label        -while-statement-10--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushI        0                         
        StoreI                                 
        Label        -while-statement-12--while-start 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        5                         
        Label        -compare-11-arg1          
        Label        -compare-11-arg2          
        Label        -compare-11-sub           
        Subtract                               
        JumpNeg      -compare-11-true          
        Jump         -compare-11-false         
        Label        -compare-11-true          
        PushI        1                         
        Jump         -compare-11-join          
        Label        -compare-11-false         
        PushI        0                         
        Jump         -compare-11-join          
        Label        -compare-11-join          
        JumpFalse    -while-statement-12--while-end 
        DLabel       _string_6_                
        DataI        3                         
        DataI        9                         
        DataI        4                         
        DataC        116                       %% "then"
        DataC        104                       
        DataC        101                       
        DataC        110                       
        DataC        0                         
        PushD        _string_6_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-statement-12--while-start 
        Label        -while-statement-12--while-end 
        DLabel       _string_5_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        97                        %% "after"
        DataC        102                       
        DataC        116                       
        DataC        101                       
        DataC        114                       
        DataC        0                         
        PushD        _string_5_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% x1
        DLabel       _string_4_                
        DataI        3                         
        DataI        9                         
        DataI        2                         
        DataC        104                       %% "hi"
        DataC        105                       
        DataC        0                         
        PushD        _string_4_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% z
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% x1
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% i
        PushI        0                         
        StoreI                                 
        Label        -while-statement-16--while-start 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% i
        LoadI                                  
        PushI        5                         
        Label        -compare-13-arg1          
        Label        -compare-13-arg2          
        Label        -compare-13-sub           
        Subtract                               
        JumpNeg      -compare-13-true          
        Jump         -compare-13-false         
        Label        -compare-13-true          
        PushI        1                         
        Jump         -compare-13-join          
        Label        -compare-13-false         
        PushI        0                         
        Jump         -compare-13-join          
        Label        -compare-13-join          
        JumpFalse    -while-statement-16--while-end 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% z
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% x1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% z
        LoadI                                  
        Label        -compare-14-arg1          
        Label        -compare-14-arg2          
        Label        -compare-14-sub           
        Subtract                               
        JumpFalse    -compare-14-true          
        Jump         -compare-14-false         
        Label        -compare-14-true          
        PushI        1                         
        Jump         -compare-14-join          
        Label        -compare-14-false         
        PushI        0                         
        Jump         -compare-14-join          
        Label        -compare-14-join          
        JumpFalse    -if-statement-15-elseBlock 
        DLabel       _string_3_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        101                       %% "equal"
        DataC        113                       
        DataC        117                       
        DataC        97                        
        DataC        108                       
        DataC        0                         
        PushD        _string_3_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% z
        DLabel       _string_2_                
        DataI        3                         
        DataI        9                         
        DataI        3                         
        DataC        98                        %% "bye"
        DataC        121                       
        DataC        101                       
        DataC        0                         
        PushD        _string_2_                
        StoreI                                 
        Jump         -if-statement-15-end      
        Label        -if-statement-15-elseBlock 
        DLabel       _string_1_                
        DataI        3                         
        DataI        9                         
        DataI        9                         
        DataC        110                       %% "not equal"
        DataC        111                       
        DataC        116                       
        DataC        32                        
        DataC        101                       
        DataC        113                       
        DataC        117                       
        DataC        97                        
        DataC        108                       
        DataC        0                         
        PushD        _string_1_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -if-statement-15-end      
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% i
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% i
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-statement-16--while-start 
        Label        -while-statement-16--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushI        0                         
        StoreI                                 
        Label        -while-statement-20--while-start 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        5                         
        Label        -compare-17-arg1          
        Label        -compare-17-arg2          
        Label        -compare-17-sub           
        Subtract                               
        JumpNeg      -compare-17-true          
        Jump         -compare-17-false         
        Label        -compare-17-true          
        PushI        1                         
        Jump         -compare-17-join          
        Label        -compare-17-false         
        PushI        0                         
        Jump         -compare-17-join          
        Label        -compare-17-join          
        JumpFalse    -while-statement-20--while-end 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% y
        PushI        0                         
        StoreI                                 
        Label        -while-statement-19--while-start 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% y
        LoadI                                  
        PushI        5                         
        Label        -compare-18-arg1          
        Label        -compare-18-arg2          
        Label        -compare-18-sub           
        Subtract                               
        JumpNeg      -compare-18-true          
        Jump         -compare-18-false         
        Label        -compare-18-true          
        PushI        1                         
        Jump         -compare-18-join          
        Label        -compare-18-false         
        PushI        0                         
        Jump         -compare-18-join          
        Label        -compare-18-join          
        JumpFalse    -while-statement-19--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% y
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% y
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% y
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-statement-19--while-start 
        Label        -while-statement-19--while-end 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-statement-20--while-start 
        Label        -while-statement-20--while-end 
        Halt                                   
