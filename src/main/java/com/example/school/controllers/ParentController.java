package com.example.school.controllers;

import com.example.school.models.Parent;
import com.example.school.models.Student;
import com.example.school.models.Attendance;
import com.example.school.models.Notification;
import com.example.school.repositories.AttendanceRepository;
import com.example.school.repositories.NotificationRepository;
import com.example.school.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ParentController {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;

 // Show Parent Dashboard
    @GetMapping("/parent/dashboard")
    public String showParentDashboard(Model model, @RequestParam int parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        // Check for notifications and initialize if null
        // Fetch only unread notifications
        List<Notification> notifications = notificationRepository.findByParentIdAndReadFalse(parentId);
        if (notifications == null) {
            notifications = List.of(); // Initializes as an empty list if null
        }

        // Add the parent and notifications to the model
        model.addAttribute("parent", parent);
        model.addAttribute("notifications", notifications);
        
        return "parent_dashboard"; // Renders parent_dashboard.html
    }


    
    @GetMapping("/parent/notifications")
    public String showNotifications(@RequestParam("parentId") int parentId, Model model) {
        // Find parent by ID
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        // Add notifications to the model
        model.addAttribute("parent", parent);
        model.addAttribute("notifications", parent.getNotifications());

        return "parent-notifications";
    }
    
    
    @PostMapping("/parent/mark-notifications-read")
    public String markNotificationsAsRead(@RequestParam int parentId) {
        notificationRepository.markNotificationsAsReadByParentId(parentId);
        return "redirect:/parent/dashboard?parentId=" + parentId; // Redirect back to dashboard
    }
    
    
    @GetMapping("/parent/children/attendance")
    public String showChildrenAttendance(@RequestParam("parentId") int parentId, Model model) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        // Get all children associated with this parent
        List<Student> children = parent.getChildrenAsParent1();
        children.addAll(parent.getChildrenAsParent2());

        // Create a map to hold attendance records for each child
        Map<Integer, List<Attendance>> attendanceMap = new HashMap<>();

        // For each child, get their attendance
        for (Student child : children) {
            List<Attendance> attendanceRecords = attendanceRepository.findByStudent(child);
            attendanceMap.put(child.getId(), attendanceRecords); // Store attendance by child ID
        }

        // Add parent, children, and attendance map to the model
        model.addAttribute("parent", parent);
        model.addAttribute("children", children);
        model.addAttribute("attendanceMap", attendanceMap); // Pass the map to the template

        return "parent_children_attendance"; // Renders parent_children_attendance.html
    }

    
    
}
