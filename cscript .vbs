'On Error Resume Next
Set objWMIService = GetObject("winmgmts:\\.\root\cimv2")
Set colItems = objWMIService.ExecQuery("Select * from Win32_PhysicalMedia",,48)
Dim objItem
For Each objItem in colItems
    WScript.Echo "SerialNumber: " & objItem.SerialNumber
Next