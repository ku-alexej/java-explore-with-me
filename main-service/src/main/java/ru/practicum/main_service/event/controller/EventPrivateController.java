package ru.practicum.main_service.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main_service.constant.Constants;
import ru.practicum.main_service.event.dto.EventFullDto;
import ru.practicum.main_service.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main_service.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.main_service.event.dto.EventShortDto;
import ru.practicum.main_service.event.dto.NewEventDto;
import ru.practicum.main_service.event.dto.ParticipationRequestDto;
import ru.practicum.main_service.event.dto.UpdateEventUserRequest;
import ru.practicum.main_service.event.service.EventService;
import ru.practicum.main_service.event.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Validated
public class EventPrivateController {

    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEventByPrivate(@PathVariable Long userId,
                                             @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.createEventByPrivate(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventByPrivate(@PathVariable Long userId,
                                          @PathVariable Long eventId) {
        return eventService.getEventByPrivate(userId, eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllEventsByPrivate(
            @PathVariable Long userId,
            @PositiveOrZero @RequestParam(defaultValue = Constants.PAGE_FROM) Integer from,
            @Positive @RequestParam(defaultValue = Constants.PAGE_SIZE) Integer size) {
        return eventService.getAllEventsByPrivate(userId, PageRequest.of(from / size, size));
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventByPrivate(@PathVariable Long userId,
                                             @PathVariable Long eventId,
                                             @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return eventService.updateEventByPrivate(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getEventRequestsByEventOwner(@PathVariable Long userId,
                                                                      @PathVariable Long eventId) {
        return requestService.getEventRequestsByEventOwner(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateEventRequestsByEventOwner(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return requestService.updateEventRequestsByEventOwner(userId, eventId, eventRequestStatusUpdateRequest);
    }

}
